<?php

namespace App\Controller;

use App\Entity\Livreur;
use App\Form\LivreurType;
use App\Repository\LivreurRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\JsonResponse;

/**
 * @Route("/livreur")
 */
class LivreurController extends AbstractController
{
    /**
     * @Route("/", name="livreur_index", methods={"GET"})
     */
    public function index(Request $request,LivreurRepository $livreurRepository,PaginatorInterface $paginator): Response
    {
        $em = $this->getDoctrine()->getManager();

        $search_input = !empty($request->get('search_input')) ? $request->get('search_input'):'';
        $query = $livreurRepository->findBySearchInput($search_input);

     
        $pagination = $paginator->paginate(
            $query, /* query

 NOT result */
            $request->query->getInt('page', 1), /*page number*/
            3 /*limit per page*/
        );
        
        return $this->render('livreur/index.html.twig', [
            'livreurs' => $pagination,
        ]);
    }

    /**
     * @Route("/new", name="livreur_new", methods={"GET","POST"})
     */
    public function new(Request $request, \Swift_Mailer $mailer): Response
    {
        $livreur = new Livreur();
        $form = $this->createForm(LivreurType::class, $livreur);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($livreur);
            $entityManager->flush();

            $message = (new \Swift_Message('Hello Email'))
            ->setFrom("houcemhamed85@gmail.com")
            ->setTo($livreur->getEmail())
            ->setBody('Operation effectuée avec succès');
            $mailer->send($message);
            
            return $this->redirectToRoute('livreur_index');
        }

        return $this->render('livreur/new.html.twig', [
            'livreur' => $livreur,
            'form' => $form->createView(),
        ]);
    }
    /**
     * @Route("/modMob/{id}/{nom}/{prenom}/{numtlf}/{cin}", name="mod", methods={"GET", "POST"})
     */
    public function modMob($id, $nom,$prenom,$numtlf,$cin)
    {
        $em = $this->getDoctrine()->getManager();
        $liv=$this->getDoctrine()->getRepository(Livreur::class)->find($id);
        $liv->setNom($nom);
        $liv->setPrenom($prenom);
        $liv->setNumtlf($numtlf);
        $liv->setCin($cin);
        $em->persist($liv);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($liv);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("/newMob", name="newMob", methods={"GET","POST"})
     */
    public function newMob(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $liv = new Livreur();
        $liv->setNom($request->get('nom'));
        $liv->setPrenom($request->get('prenom'));
        $liv->setCin($request->get(('cin')));
        $liv->setEmail($request->get(('email')));
        $liv->setNumtlf($request->get('numtlf'));
        $liv->setDisponibilite($request->get('disponibilite'));
        $em->persist($liv);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($liv);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/deletelivMob/{id}", name="deletelivMob")
     */
    public function deletelivMob($id)
    {
        $em = $this->getDoctrine()->getManager();
        $liv = $em->getRepository(Livreur::class)->find($id);
        $em->remove($liv);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($liv);
        return new JsonResponse($formatted);
    }


    /**
     * @Route("/Mobi", name="Mobi", methods={"GET"})
     */
    public function Mobi(Request $request)
    {
        $m = $this->getDoctrine()->getManager();
        $Livreur = $m->getRepository(Livreur::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($Livreur);
        return new JsonResponse($formatted);
    }





    /**
     * @Route("/edit/{id}", name="livreur_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Livreur $livreur): Response
    {
        $form = $this->createForm(LivreurType::class, $livreur);
        $form->handleRequest($request);
     
        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('livreur_index');
        }  
        dump($form->createView());
        dump($livreur);
        return $this->render('livreur/update.html.twig', [
            'livreur' => $livreur,
            'form' => $form->createView(),
        ]);
    }

  

    /**
     * @Route("/delete/{id}", name="livreur_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Livreur $livreur): Response
    {
        if ($this->isCsrfTokenValid('delete'.$livreur->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($livreur);
            $entityManager->flush();
        }

        return $this->redirectToRoute('livreur_index');
    }

    /**
     * @Route("/{id}", name="livreur_show", methods={"GET"})
     */
    public function show(Livreur $livreur): Response
    {
        return $this->render('livreur/show.html.twig', [
            'livreur' => $livreur,
        ]);
    }


}