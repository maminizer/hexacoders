<?php

namespace App\Controller;

use App\Entity\Item;
use App\Entity\Panier;
use App\Entity\Product;
use App\Repository\ItemRepository;
use App\Repository\ProductRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

/**
 * @Route("/item")
 */
class ItemController extends AbstractController
{
    /**
     * @param Product $product
     * @Route("/add/{id}", name="add_item")
     * @var \App\Entity\User $user
     */
    public function add(Product $id, Request $request, ItemRepository $itemRepository, ProductRepository $product): Response
    {


        /* we have 2 main cases :
            1 - Panier doesnt exist
            2 - Panier exists
                2.1 - Item exists in Panier
                2.2 - Item doesn't exist in Panier
            */

        //logic shared between 2 cases

        //Get current user
        $user = $this->getUser();

        $em = $this->getDoctrine()->getManager();
        $itemProduct = $product->find(['id' => $id]);


        // Case 1 : Panier doesn't exist
        if (Count($user->getItems()) == 0) {   //empty cart
            $item = new Item();                //create new Item
            $item->setProduct($itemProduct);   //set items product id
            $item->setUser($user);             //set items user id
            $panier = new Panier();            //Create new Panier
            $item->setQuantity(1);      //set Quantity to 1
            $item->setPanier($panier);         //set items panier id
            $panier->addItem($item);           // add current item to Panier
            $em->persist($panier);             // persist Panier
            $em->persist($item);               // persist Item
            $em->flush();                      // flush Panier and item
        }

        // Case 2 : Panier already exists
        else {

            $panier  = $user->getItems()[0]->getPanier(); //item exists in panier

            $items = $user->getItems(); //get all items associated with the current User

            foreach ($items as $item) { //check if item's product id equals to passed product id in param

                $panier = $user->getItems()[0]->getPanier(); //Case 2.1: Item exists in Panier
                if ($item->getProduct()->getId() == $id->getId() && $item->getPanier()->getId() == $panier->getId()) { // table item product and cart in same line
                    $item->setQuantity($item->getQuantity() + 1);
                    $em->persist($panier);
                    $em->flush();

                    return $this->redirectToRoute("panier");
                }
            }
            // Case 2.2 : Item doesn't exists in Panier
            $item = new Item();
            $item->setProduct($itemProduct);
            $item->setUser($user);             //set items user id
            $item->setQuantity(1);      //set Quantity to 1
            $item->setPanier($panier);         //set items panier id
            $panier->addItem($item);           // add current item to Panier
            $em->persist($panier);             // persist Panier
            $em->persist($item);               // persist Item
            $em->flush();
        }


        return $this->redirectToRoute("panier");
    }

    /**
     * @Route("/remove/{id}", name="remove_item" )
     */
    public function remove(Item $id)
    {
        //$item = $itemRepository->find(['id' => $id]);
        $em = $this->getDoctrine()->getManager();
        $em->remove($id);
        $em->flush();

        return $this->redirectToRoute("panier");
    }


    /**
     * @Route("/inc/{id}", name="increase_item")
     */
    public function increase(Item $id)
    {
        $em = $this->getDoctrine()->getManager();
        $id->setQuantity($id->getQuantity() + 1);
        $em->persist($id);
        $em->flush();

        return $this->redirectToRoute("panier");
    }

    /**
     * @Route("/dec/{id}", name="decrease_item")
     */
    public function decrease(Item $id)
    {
        $em = $this->getDoctrine()->getManager();
        if ($id->getQuantity() == 1) {
            $em->remove($id);
        } else {
            $id->setQuantity($id->getQuantity() - 1);
            $em->persist($id);
        }
        
        $em->flush();
        return $this->redirectToRoute("panier");
    }

    
}
