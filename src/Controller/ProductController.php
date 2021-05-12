<?php

namespace App\Controller;

use App\Data\FiltreData;
use App\Entity\Commentaire;
use App\Entity\Product;
use App\Entity\Productlike;
use App\Form\CommentaireType;
use App\Form\FiltreType;
use App\Form\ProductType;
use App\Repository\CatalogueRepository;
use App\Repository\ProductlikeRepository;
use App\Repository\ProductRepository;
use App\Repository\CommentaireRepository;
use App\Repository\UserRepository;
use Doctrine\Persistence\ObjectManager;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Form\Extension\Core\Type\FileType;



class ProductController extends AbstractController
{
  

   
    



}
