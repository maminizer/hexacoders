<?php

namespace App\Controller;

use App\Data\SearchData;
use App\Data\FiltreData;
use App\Entity\Produit;
use App\Form\FiltreForm;
use App\Form\ProduitType;
use App\Form\SearchForm;
use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizableInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class ProduitController extends AbstractController
{
    /**
     * @Route("/produit", name="produit")
     */
    public function index(): Response
    {
        return $this->render('produit/index.html.twig', [
            'controller_name' => 'ProduitController',
        ]);
    }
    /**
     * @Route("produit/affiche",name="AfficheProduit")
     */
    public function AfficheProduit(ProduitRepository $repository){


        $produit=$repository->findAll();

        return $this->render('produit/affiche.html.twig',
            ['produit'=>$produit]
        );
    }
    /**
     * @Route("produit/search",name="search")
     */
    public function searchProduit(ProduitRepository $repository, Request $request, NormalizerInterface $Normalizer){
        $repo=$this->getDoctrine()->getRepository(Produit::class);
        $requestString=$request->get('searchValue');
        $produit = $repository->findProduitByNom($requestString);
        $jsonContent = $Normalizer->normalize($produit, 'json',['groups'=>'produit']);
        $retour=json_encode($jsonContent);
        return new Response($retour);
    }


    /**
     * @Route("produit/afficheClient",name="AfficheProduitClient")
     */
    public function AfficheProduitClient(ProduitRepository $repository,Request $request){
        //$repo=$this->getDoctrine()->getRepository(Classroom::class);
        $produit=$repository->findAll();
        $data = new SearchData();
        $form = $this->createForm(SearchForm::class, $data);
        $form->handleRequest($request);
        $produit=$repository->findSearch($data);
        $data->page = $request->get('page', 1);



        $dataF = new FiltreData();
        $formF = $this->createForm(FiltreForm::class, $dataF);
        $formF->handleRequest($request);
        $produit=$repository->findByFiltre($dataF);
        return $this->render('produit/AfficheClient.html.twig', [
            'produit'=>$produit,
            'formRecherche' => $form->createView(),
            'formFiltre' => $formF->createView()
            ]);
    }

    /**
     * @Route("produit/afficheClient/{id}",name="AfficheProduitClientDetaill")
     */
    public function AfficheDetaillClient(ProduitRepository $repository,$id){
        //$repo=$this->getDoctrine()->getRepository(Classroom::class);
        $produit=$repository->find($id);
        return $this->render('produit/AfficheDetaill.html.twig',
            ['produit'=>$produit]);
    }
    /**
     * @Route("produit/afficheAdmin/{id}",name="AfficheProduitAdminDetaill")
     */
    public function AfficheDetaillAdmin(ProduitRepository $repository,$id){
        //$repo=$this->getDoctrine()->getRepository(Classroom::class);
        $produit=$repository->find($id);
        return $this->render('produit/AfficheDetaillAdmin.html.twig',
            ['produit'=>$produit]);
    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("produit/add" , name="ajout")
     */
    public function AjoutProduit(Request $request){
        $produit= new Produit();
        $form=$this->createForm(ProduitType::class,$produit);
        $form->add('Ajouter',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file = $produit->getImage();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            try{
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e){

            }
            $em=$this->getDoctrine()->getManager();
            $produit->setImage($fileName);
            $em->persist($produit);
            $em->flush();
            return $this->redirectToRoute('AfficheProduit');
        }
        return $this->render('produit/Add.html.twig',[
            'form'=>$form->createView()
        ]);
    }
    /**
     * @Route("/Supp/{id}", name="d")
     */
    public function Delete($id, ProduitRepository $repository){
        $produit=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($produit);
        $em->flush();
        return $this->redirectToRoute('AfficheProduit');
    }
    /**
     * @Route("produit/Update/{id}", name="update")
     */
    function Update(ProduitRepository $repository,$id,Request $request){
        $produit=$repository->find($id);
        $form=$this->createForm(ProduitType::class,$produit);
        $form->add('Update',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file = $produit->getImage();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            try{
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e){

            }
            $em=$this->getDoctrine()->getManager();
            $produit->setImage($fileName);
            $em->flush();
            return $this->redirectToRoute('AfficheProduit');
        }
        return $this->render('produit/Update.html.twig',[
            'formU'=>$form->createView()
        ]);
    }

}
