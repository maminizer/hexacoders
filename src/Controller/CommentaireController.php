<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Product;
use App\Entity\Commentaire;
use App\Form\CommentaireType;
use App\Entity\CommentaireLike;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\CommentaireRepository;
use Symfony\Component\Serializer\Serializer;
use App\Repository\CommentaireLikeRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;


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
    public function AfficheCommentaire(CommentaireRepository $repository)
    {
        //$repo=$this->getDoctrine()->getRepository(Classroom::class);
        $commentaire = $repository->findAll();
        return $this->render(
            'admin/commentaire/affiche.html.twig',
            ['commentaire' => $commentaire]
        );
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("commentaire/addC", name="ajoutCommentaireC")
     */
    public function AjoutCommentaire(Request $request)
    {
        $commentaire = new Commentaire();
        $form = $this->createForm(CommentaireType::class, $commentaire);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $pid = $commentaire->getProduit();
            $pidd = $pid->getId();
            $em->persist($commentaire);
            $em->flush();
            return $this->redirectToRoute('AfficheProduitClientDetaill', ['id' => $pidd]);
        }
        return $this->render('product/AfficheDetaill.html.twig', [
            'formCommentaire' => $form->createView()
        ]);
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("commentaire/add", name="ajoutCommentaireA")
     */
    public function AjoutCommentaireAdmin(Request $request)
    {
        $commentaire = new Commentaire();
        $form = $this->createForm(CommentaireType::class, $commentaire);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $pid = $commentaire->getProduit();
            $pidd = $pid->getId();
            $em->persist($commentaire);
            $em->flush();
            return $this->redirectToRoute('AfficheProduitAdminDetaill', ['id' => $pidd]);
        }
        return $this->render('commentaire/add.html.twig', [
            'formCommentaire' => $form->createView()
        ]);
    }

    /**
     * @Route("commentaire/Update/{id}", name="modifierCommentaire")
     */
    function Update(CommentaireRepository $repository, $id, Request $request)
    {
        $commentaire = $repository->find($id);
        $form = $this->createForm(CommentaireType::class, $commentaire);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $pid = $commentaire->getProduit();
            $pidd = $pid->getId();
            $em->flush();
            return $this->redirectToRoute('AfficheProduitClientDetaill', ['id' => $pidd]);
        }
        return $this->render('commentaire/updateC.html.twig', [
            'f' => $form->createView()
        ]);
    }
    /**
     * @Route("commentaire/UpdateAdmin/{id}", name="modifierCommentaireAdmin")
     */
    function UpdateAdmin(CommentaireRepository $repository, $id, Request $request)
    {
        $commentaire = $repository->find($id);
        $form = $this->createForm(CommentaireType::class, $commentaire);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $pid = $commentaire->getProduit();
            $pidd = $pid->getId();
            $em->flush();
            return $this->redirectToRoute('AfficheProduitAdminDetaill', ['id' => $pidd]);
        }
        return $this->render('commentaire/updateC.html.twig', [
            'f' => $form->createView()
        ]);
    }

    /**
     * Permet de likez et disliker un comment
     *
     * @Route ("/commentaire/{id}/like", name="commentaire_like")
     *
     * @param Commentaire $commentaire
     * @param EntityManagerInterface $manager
     * @param CommentaireLikeRepository $likeRepo
     * @return Response
     */
    public function like(Commentaire $commentaire, CommentaireLikeRepository $likeRepo): Response
    {
        $em = $this->getDoctrine()->getManager();

        $user = $this->getUser();
        if (!$user) return $this->json([
            'code' => 403,
            'message' => "unauthorized"
        ], 403);
        //produit deja aimé
        //supprimer un like pour un user
        if ($commentaire->isLikedByUser($user)) {
            $like = $likeRepo->findOneBy([
                'commentaire' => $commentaire,
                'User' => $user
            ]);
            $em->remove($like);
            $em->flush();
            return $this->json([
                'code' => 200,
                'message' => "LIKE BIEN SUPPRIME",
                'likes' => $likeRepo->count(['commentaire' => $commentaire])
            ], 200);
        }
        //créer un nouveau like
        $like = new CommentaireLike();
        $like->setCommentaire($commentaire)
            ->setUser($user);

        $em->persist($like);
        $em->flush();

        return $this->json([
            'code' => 200,
            'message' => 'ca maeche bien ajouté',
            'likes' => $likeRepo->count(['commentaire' => $commentaire])
        ], 200);
    }

    /******************Ajouter commentaire*****************************************/
    /**
     * @Route("/addComment", name="add_comment" , methods={"GET","POST"})
     *
     */
    public function ajouterCommentaireAction(Request $request)
    {
        //http://127.0.0.1:8000/addComment?id=21&contenu=hihihihihohohoh
        $id = $request->get("id");
        $iduser = $request->get("user");
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(User::class)->find($iduser);
        $product = $this->getDoctrine()->getManager()->getRepository(Product::class)->find($id);
       
        $commenatire = new Commentaire();
        // $produit = $request->query->get("produit");
        $contenu = $request->query->get("contenu");
        $em = $this->getDoctrine()->getManager();
        $commenatire->setProduit($product);
        $commenatire->setContenu($contenu);
        $commenatire->setUser($user);
        $em->persist($commenatire);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        //$formatted = $serializer->normalize($commenatire);
        //return new JsonResponse($formatted);
        return $this->json($commenatire, Response::HTTP_OK, [], [
            ObjectNormalizer::IGNORED_ATTRIBUTES => [
                'likes',
                'user'
            ],
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            }
        ]);
    }

    /******************affichage Commentaire*****************************************/

    /**
     * @Route("/displayComments", name="display_comments")
     */
    public function allCommentAction(SerializerInterface $serializer, Request $request)
    {
        //http://127.0.0.1:8000/displayComments?id=21
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $product = $this->getDoctrine()->getManager()->getRepository(Product::class)->find($id);
        //les commentaires d'un produit quelconque
        $commentaires = $product->getCommentaires();
        $serializer = new Serializer([new ObjectNormalizer()]);
        //$formatted = $serializer->normalize($product);
        // return new JsonResponse($formatted);
        return $this->json($commentaires, Response::HTTP_OK, [], [
            ObjectNormalizer::IGNORED_ATTRIBUTES => [
                'likes',
                'user'
            ],
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            }
        ]);
    }
}
