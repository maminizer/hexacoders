<?php

namespace App\Controller;

use App\Repository\ItemRepository;
use App\Repository\ProductRepository;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

class TestController extends AbstractController
{
    /**
     * @Route("/test", name="test")
     */
    public function index(ItemRepository $itemRepository, ProductRepository $productRepository): Response
    {
        //Get current user
        $user = $this->getUser();
        $items = $itemRepository->findBy([
            'user' => $user
        ]);
        $total = 0 ;
        foreach ($items as $item){
            $i = $item->getProduct()->getPrice();
            $qte = $item->getQuantity();
            $total = ($i * $qte) + $total;
        }
        return $this->render('test/index.html.twig', [
            'items' => $items,
            'total' => $total 
        ]);
    }

}
