<?php

namespace App\Controller;

use App\Entity\Commentaire;
use App\Form\CommentaireType;

use App\Repository\CommentaireRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class CommentaireController extends AbstractController
{
    /**
     * @Route("/commentaire", name="commentaire")
     */
    public function index(): Response
    {
        return $this->render('commentaire/index.html.twig', [
            'controller_name' => 'CommentaireController',
        ]);
    }
    /**
     * @Route("commentaire/affiche",name="AfficheCommentaire")
     */
    public function AfficheCommentaire(CommentaireRepository $repository){
        //$repo=$this->getDoctrine()->getRepository(Classroom::class);
        $commentaire=$repository->findAll();
        return $this->render('commentaire/affiche.html.twig',
            ['commentaire'=>$commentaire]);
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("commentaire/addC", name="ajoutCommentaireC")
     */
    public function AjoutCommentaire(Request $request){
        $commentaire= new Commentaire();
        $form=$this->createForm(CommentaireType::class,$commentaire);

        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $pid=$commentaire->getProduit();
            $pidd=$pid->getId();
            $em->persist($commentaire);
            $em->flush();
            return $this->redirectToRoute('AfficheProduitClientDetaill', [ 'id' => $pidd]);
        }
        return $this->render('commentaire/add.html.twig',[
            'formCommentaire'=>$form->createView()
        ]);
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("commentaire/add", name="ajoutCommentaireA")
     */
    public function AjoutCommentaireAdmin(Request $request){
        $commentaire= new Commentaire();
        $form=$this->createForm(CommentaireType::class,$commentaire);

        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $pid=$commentaire->getProduit();
            $pidd=$pid->getId();
            $em->persist($commentaire);
            $em->flush();
            return $this->redirectToRoute('AfficheProduitAdminDetaill', [ 'id' => $pidd]);
        }
        return $this->render('commentaire/add.html.twig',[
            'formCommentaire'=>$form->createView()
        ]);
    }
    /**
     * @Route("/Supp/{id}", name="SupprimerCommentaire")
     */
    public function Delete($id, CommentaireRepository $repository){
        $commentaire=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        if(($commentaire->getProduit()) == null){
            return $this->redirectToRoute('AfficheProduit');
        }
        else{
            $pid=$commentaire->getProduit();
            $pidd=$pid->getId();
            $em->remove($commentaire);
            $em->flush();
            return $this->redirectToRoute('AfficheProduitAdminDetaill', [ 'id' => $pidd]);
        }
    }
    /**
     * @Route("commentaire/Update/{id}", name="modifierCommentaire")
     */
    function Update(CommentaireRepository $repository,$id,Request $request){
        $commentaire=$repository->find($id);
        $form=$this->createForm(CommentaireType::class,$commentaire);

        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $pid=$commentaire->getProduit();
            $pidd=$pid->getId();
            $em->flush();
            return $this->redirectToRoute('AfficheProduitClientDetaill', [ 'id' => $pidd]);
        }
        return $this->render('commentaire/updateC.html.twig',[
            'f'=>$form->createView()
        ]);
    }
    /**
     * @Route("commentaire/UpdateAdmin/{id}", name="modifierCommentaireAdmin")
     */
    function UpdateAdmin(CommentaireRepository $repository,$id,Request $request){
        $commentaire=$repository->find($id);
        $form=$this->createForm(CommentaireType::class,$commentaire);

        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $pid=$commentaire->getProduit();
            $pidd=$pid->getId();
            $em->flush();
            return $this->redirectToRoute('AfficheProduitAdminDetaill', [ 'id' => $pidd]);
        }
        return $this->render('commentaire/updateC.html.twig',[
            'f'=>$form->createView()
        ]);
    }



}
