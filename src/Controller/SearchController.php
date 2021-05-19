<?php

namespace App\Controller;

use App\Data\SearchData;
use App\Entity\Categorie;
use App\Form\SearchType;
use App\Repository\CategorieRepository;
use App\Repository\ProductRepository;

use Knp\Component\Pager\PaginatorInterface;
use Doctrine\ORM\Tools\Pagination\Paginator;
use phpDocumentor\Reflection\Types\Context;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class SearchController extends AbstractController
{
    /**
     * @Route("/search", name="search")
     */
    public function index(ProductRepository $productRepository, Request $request, PaginatorInterface $paginator): Response
    {
        $data = new SearchData();
        $form =$this->createForm(SearchType::class, $data);
        $form->handleRequest($request);

        [$min , $max] =$productRepository->findMinMax($data);
        $products =$productRepository->findSearch($data);

        $articles = $paginator->paginate(
            $products,
            $request->query->getInt('page', 1),
            3
        );
        return $this->render('search/index.html.twig', [
            'articles' => $articles,
            'form' => $form->createView(),
            'min' => $min,
            'max' => $max
            
        ]);
    }

    /**
     * @Route("/searchp/{id}", name="searchp")
     */
    public function searchP(ProductRepository $productRepository, Request $request, PaginatorInterface $paginator, CategorieRepository $categorieRepository, Categorie $id): Response
    {

        $products = $productRepository->findBy([
                'categorie' => $categorieRepository->find($id)
                ]);

        $articles = $paginator->paginate(
            $products,
            $request->query->getInt('page', 1),
            2
        );

        return $this->render('search/navsearch.html.twig', [
            'articles' => $articles,

        ]);
    }
}
