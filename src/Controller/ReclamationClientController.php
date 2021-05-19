<?php

namespace App\Controller;
use App\Entity\Reclamations;
use App\Entity\ReclamationAdmin;
use App\Form\ReponseAdminType;
use App\Form\ReclamationType;
use App\Repository\ReclamationsRepository;
use App\Repository\ReclamationAdminRepository;
use Dompdf\Dompdf;
use Dompdf\Options;
use Doctrine\ORM\EntityManager;
use phpDocumentor\Reflection\Type;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\Count;


class ReclamationClientController extends AbstractController
{
    

    /**
     * @param Reclamations $repository
     * @return Response
     * @Route ("/AfficheA" , name="AfficheA")
     */

    function Affiche(ReclamationsRepository $repository)
    {

        $reclamation = $repository->findAll();

        return $this->render('reclamation_client/aff.html.twig', ['reclamation' => $reclamation]);


    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("/recla")
     */

    function Add(Request $request)
    {

        $reclamations = new reclamations ();
        $form = $this->createForm(ReclamationType::class, $reclamations);
        $form->Add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $reclamations->setEtat(1);
            $em->persist($reclamations);
            $em->flush();
            return $this->redirectToRoute('AfficheA');

        }
        return $this->render('reclamation_client/Envoyer.html.twig', ['form' => $form->createView()]);

    }

    /**
     * @param ReclamationsRepository $repo
     * @return Response
     * @Route ("/reps", name="reps")
     */

    public function AfficheAdmin(ReclamationsRepository $repo)
    {

        $reclamations = $repo->findAll();

        return $this->render('reclamation_client/rep.html.twig', ['reclamations' => $reclamations]);


    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("/repond" , name="repond")
     */

    function ReponseAdmin(Request $request)
    {
        $id = $request->query->get('id');
        $reclamationAdmin = new reclamationAdmin ();
        $form = $this->createForm(ReponseAdminType::class, $reclamationAdmin);
        $form->add('Repondre', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $reclamation = $this->getDoctrine()->getRepository(Reclamations::class)->find($id);
            $reclamation->setEtat(2);
            $em = $this->getDoctrine()->getManager();
            $em->persist($reclamationAdmin);
            $em->persist($reclamation);
            $em->flush();
            return $this->redirectToRoute('reps');
        }
        return $this->render("reclamation_client/repondre.html.twig", array('form' => $form->createView()));

    }

    /**
     * @param ReclamationAdminRepository $repo
     * @return Response
     * @Route ("/Admin{id}", name="Admin")
     */

    public function AfficheReponse(ReclamationAdminRepository $repo , $id)
    {
        $reclamationAdmins = $repo->findAll();


        return $this->render('reclamation_client/AfficheReponse.html.twig', ['reclamationAdmins' => $reclamationAdmins]);


    }
    


    /**
     * @Route ("/fichepdf/{id}" , name="fichepdf")
     */

    function FichierPdf(Reclamations $id)

    {

        $repo=$this->getDoctrine()->getRepository(Reclamations::class);

        $reclamation=$repo->find($id);
        //dd($reclamation);
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);


        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('reclamation_client/fichpdf.html.twig', ['r' => $reclamation  ]);


        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);
    }









    public function home(){


        return $this->render('base.html.twig' );



    }


}