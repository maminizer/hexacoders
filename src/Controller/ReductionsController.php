<?php

namespace App\Controller;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
use App\Entity\Produit;
use App\Entity\Offre;
use App\Form\OffreType;
use App\Repository\OffreRepository;
use App\Repository\ProduitRepository;
use Doctrine\ORM\EntityManager;
use phpDocumentor\Reflection\Type;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


class ReductionsController extends AbstractController
{


    /**
     * @param OffreRepository $repository
     * @return Response
     * @Route ("/reduc" , name="reduc")
     */

    function ReductionPrix(OffreRepository $repository)
    {
        $Offre = $repository->findAll();

        return $this->render('reductions/reduct.html.twig', ['Offre' => $Offre]);


    }


    /**
     * @param Request $request
     * @return Response
     * @Route ("/promo")
     */

    function add(Request $request)
    {

        $offre = new offre();
        $form = $this->createForm(OffreType::class, $offre);
        $form->add('Ajouter', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($offre);
            $em->flush();


            //mailing
            $mail = new PHPMailer(true);

            try {

                $email = $form->get('mail')->getData();

                //Server settings
                $mail->SMTPDebug = SMTP::DEBUG_SERVER;
                $mail->isSMTP();
                $mail->Host       = 'smtp.gmail.com';
                $mail->SMTPAuth   = true;
                $mail->Username   = 'faroukgasaraa@gmail.com';             // SMTP username
                $mail->Password   = 'farouk1998';                               // SMTP password
                $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
                $mail->Port       = 587;

                //Recipients
                $mail->setFrom('faroukgasaraa@gmail.com', 'Tunipharma');
                $mail->addAddress($email, 'Tunipharma Client ');     // Add a recipient
                // Content
                $corps="Bonjour Monsieur/Madame on a ajouter une Offre " ;
                $mail->isHTML(true);                                  // Set email format to HTML
                $mail->Subject = 'Offre';
                $mail->Body    = $corps;

                $mail->send();
                $this->addFlash('message','the email has been sent');

            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }



            //end mailing







            return $this->redirectToRoute('reduc');

        }
        return $this->render('reductions/Affiche.html.twig', ['form' => $form->createView()]);

    }

    /**
     * @Route ("/modifier{id}" ,  name="modifier")
     */

    function Update(OffreRepository $repository,$id,Request $request):Response
    {
        $offre=$repository->find($id);
        $form=$this->createForm(OffreType::class,$offre);
        $form->add('Update',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('reduc');
        }
        return $this->render("reductions/modifier.html.twig",array('form'=>$form->createView()));

    }





}



