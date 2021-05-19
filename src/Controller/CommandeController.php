<?php

namespace App\Controller;

use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Commande;
use App\Repository\CommandeRepository;
use App\Repository\ProductRepository;
use Symfony\Component\Mime\Emai;

class CommandeController extends AbstractController
{
    /**
     * @Route("/commande", name="commande")
     */
    public function index(CommandeRepository $commandeRepository , ProductRepository $productRepository, MailerInterface $mailer): Response
    {
        $em = $this->getDoctrine()->getManager();

        $commandes = $commandeRepository->findBy([
            'idUser' => $this->getUser(),
            'validated' => 0
        ],
            ['createdAt'=> 'DESC']
       );

        $sk = 'sk_test_51IV2PtABbOaXQW4MdA6awH3XFwzbxRfTGhGcjwpzZrAobSWZXY9L4yhnhuLOGAZgZfLcYqRb9YZVVSUSdBtnRA4a00zk49mi2N';
        \Stripe\Stripe::setApiKey($sk);
        $intent = \Stripe\PaymentIntent::all([

        ]);

        foreach ($commandes as $commande) {
            if ($intent['data'][0]['status'] == 'succeeded') {

                $commande->setValidated(1);
                $em->flush();
            }

        }



        //email starts here
        $command_all = array();
        $i = 0;

        $commandmail_ref = $commandeRepository->findOneBy([
            'idUser' => $this->getUser(),
            'validated' => 1
        ],
            ['createdAt'=> 'DESC']
        );

        $command_mail = $commandeRepository->findBy([
            'idUser' => $this->getUser(),
            'validated' => 1,
            'createdAt' => $commandmail_ref->getCreatedAt(),
        ]);
        //dd($command_mail);

        foreach ($command_mail as $commande){

            $product = $productRepository->findOneBy([
                'id' => $commande->getIdProduct()
            ]);
            $command_all[$i] = [
                'productTitle' =>  $product->getTitle(),
                'productId'    =>  $product->getId(),
                'productDes'   =>   $product->getDescription(),
                'productImage' =>  $product->getImage(),
                'quantity'     =>  $commande->getQuantity(),
                'prix'         =>  $product->getPrice(),
                'createdAt'    =>  $commande->getCreatedAt()
            ];
            $i = $i + 1;

        }

        //creating mail template
        $user_mail = $this->getUser()->getEmail();

        //dd($user_mail);

        $email = (new TemplatedEmail())
            ->from('amine1997mami@gmail.com')
            ->to($user_mail)
            ->subject('Envoie de la commande')
            ->htmlTemplate('commande/command_mail.html.twig')
            ->context([
                'commande' =>$command_all
            ]);


        $mailer->send($email);




        return $this->redirectToRoute("commandall");
    }



    public function commande(CommandeRepository $commandeRepository) {

        $commandes = $commandeRepository->findBy([
            'idUser' => $this->getUser(),
            'validated' => 1
        ]);
        $sortedcommands = $commandeRepository->findbytime();
        dd($sortedcommands);

    }

    /**
     
     * @Route("/commandall", name="commandall")
     */
    public function commandeGroup(CommandeRepository $commandeRepository ,ProductRepository $productRepository)
    {   
        // Get all commands in order of Purchase Date
        $commandes = $commandeRepository->findBy([
            'idUser'=> $this->getUser(),
            'validated' => 1,
        ],
        ['createdAt'=> 'DESC']
        );
        $commandAll = array();
        if ($commandes){
        // Initialize an array and fill its first index with the first command
        
        $id = $commandes[0]->getIdProduct();
        $product = $productRepository->find($id);
       // dd($commandes[0]);
        $commandAll[0][0] = [
            'id'           =>  $commandes[0]->getId(),
            'idProduct'    =>  $product->getId(),
            'productTitle' =>  $product->getTitle(),
            'productImage' =>  $product->getImage(),
            'quantity'     =>  $commandes[0]->getQuantity(),
            'prix'         =>  $product->getPrice(),
            'createdAt'    =>  $commandes[0]->getCreatedAt()
        ];
        //dd($commandAll);
        $i =0; $j=0;
     
        foreach ($commandes as $commande){
            // Check if this command and previous command got same purchase date
            $previous_commande_date = $commandAll[$i][$j]['createdAt'];
            $commande_date = $commande->getCreatedAt();

            // extra condition on Command Id so the first item don't get added twice
            $previous_commande_id = $commandAll[$i][$j]['id'];
            $commande_id = $commande->getId();


            if ($commande_date == $previous_commande_date and $commande_id !=$previous_commande_id) 
            {   
                $j = $j+1;
                $id = $commande->getIdProduct();
                $product = $productRepository->find($id);
                $commandAll[$i][$j] = [
                    'id'           =>  $commande->getId(),
                    'idProduct'    =>  $product->getId(),
                    'productTitle' =>  $product->getTitle(), 
                    'productImage' =>  $product->getImage(),
                    'quantity'     =>  $commande->getQuantity(),
                    'prix'         =>  $product->getPrice(),
                    'createdAt'    =>  $commande->getCreatedAt()
                ] ;
            }
            else if ($commande_date != $previous_commande_date and $commande_id !=$previous_commande_id) {

                $i = $i+1 ;
                $id = $commande->getIdProduct();
                $product = $productRepository->find($id);
                $commandAll[$i][$j] = [
                    'id'           =>  $commande->getId(),
                    'idProduct'    =>  $product->getId(),
                    'productTitle' =>  $product->getTitle(), 
                    'productImage' =>  $product->getImage(),
                    'quantity'     =>  $commande->getQuantity(),
                    'prix'         =>  $product->getPrice(),
                    'createdAt'    =>  $commande->getCreatedAt()
                ] ;
            }

                   
        }
        //dd($commandAll);

        return $this->render('commande/all_comands.html.twig', [
            'commandes' => $commandAll,
        ]);
        }
    }
}
