<?php

namespace App\Controller;

use DateTime;
use App\Entity\Sold;
use App\Entity\User;
use App\Entity\Panier;
use App\Entity\Product;
use App\Entity\Commande;
use App\Repository\ItemRepository;
use App\Repository\SoldRepository;
use App\Repository\UserRepository;
use App\Repository\PanierRepository;
use App\Repository\ProductRepository;
use App\Repository\CommandeRepository;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;



class SoldController extends AbstractController
{
    /**
     * @Route("/sold", name="Sold")
     *
     */
    public function index(ItemRepository $itemRepository, SoldRepository $soldRepository): Response
    {
        // case product not sold



        $Products = $itemRepository->findBy([
            'user' => $this->getUser()
        ]);

        $total = 0 ;
        foreach ($Products as $product){
            $prods = $product->getProduct()->getPrice();
            $qte = $product->getQuantity();
            $total = ($prods * $qte) + $total;
        }





        $em = $this->getDoctrine()->getManager();

        foreach ($Products as $product){

            $sold_Product = $soldRepository->findProduct($product->getProduct()->getId());

                if(! $sold_Product){
                    $sold = new Sold();


                    $sold->setIdProduit($product->getProduct()->getId());
                    $sold->setQteSold($product->getQuantity());
                    $sold->setImage($product->getProduct()->getImage());
                    $sold->setTitle($product->getProduct()->getTitle());
                    $sold->setPrixUnitaire($product->getProduct()->getPrice());


                    //gestion des commandes
                    $commande = new Commande();
                    $commande->setIdUser($this->getUser()->getId());
                    $commande->setIdProduct($product->getProduct()->getId());
                    $commande->setQuantity($product->getQuantity());
                    $date = new \DateTime("now");
                    $dates = $date->format('Y-m-d H:i');
                    $commande->setCreatedAt($dates);
                    $commande->setValidated(0);
                    $em->persist($commande);



                    $em->persist($sold);



                    $em->remove($product);
                    $em->flush();

                }else {
                    $sold_Product->setQteSold($sold_Product->getQteSold() + $product->getQuantity());
                    $em->persist($sold_Product);
                    $em->remove($product);


                    //gestion des commandes
                    $commande = new Commande();
                    $commande->setIdUser($this->getUser()->getId());
                    $commande->setIdProduct($product->getProduct()->getId());
                    $commande->setQuantity($product->getQuantity());
                    $date = new \DateTime("now");
                    $dates = $date->format('Y-m-d H:i');
                    $commande->setCreatedAt($dates);
                    $commande->setValidated(0);
                    $em->persist($commande);



                    $em->flush();
                }

            }

        
        require_once('C:\Users\maminizer\Desktop\form8.2 - Copie\vendor\autoload.php');

        $prix = $total;

        /* on instancie stripe */
        \Stripe\Stripe::setApiKey('sk_test_51IV2PtABbOaXQW4MdA6awH3XFwzbxRfTGhGcjwpzZrAobSWZXY9L4yhnhuLOGAZgZfLcYqRb9YZVVSUSdBtnRA4a00zk49mi2N');

        $intent = \Stripe\PaymentIntent::create([
            'amount' => $prix * 100,
            'currency' => 'eur'
        ]);

        $sk = $intent['client_secret'];

        return $this->render('sold/index.html.twig', [
            'total' => $total,
            'intent' => $intent,
            'sk' => $sk
        ]);
    }


    /**
     * @Route("/admin/sold", name="most_purchased")
     */
    public function most_purchased(SoldRepository $soldRepository){
        $prods = $soldRepository->OrderByQte();
        return $this->render("admin/sold/index.html.twig",[
            'prods' =>$prods
        ]);
    }

    public function top_products(SoldRepository $soldRepository){
        $prods = $soldRepository->OrderByQte();
        return $this->render("components/topproducts.html.twig",[
            'prods' =>$prods
        ]);
    }
}
