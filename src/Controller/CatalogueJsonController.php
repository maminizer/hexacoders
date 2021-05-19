<?php

namespace App\Controller;

use App\Entity\Catalogue;

use App\Repository\CatalogueRepository;

use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;

class CatalogueJsonController extends AbstractController
{
    /**
     * @Route("/catalogue", name="catalogue")
     */
    public function index(): Response
    {
        return $this->render('catalogue/index.html.twig', [
            'controller_name' => 'CatalogueController',
        ]);
    }


    //afficher les catalogue
    ///**
    //* @Route("/catalogueAll",name="catalogueAll")
    // */
    /*public function afficheClientCatalogue(NormalizerInterface $inter)
    {
        $repo = $this->getDoctrine()->getRepository(Catalogue::class);
        $da = $repo->findAll();
         $json=$inter->normalize($da,'json',['groups'=>'data']);
        return new Response(json_encode($json));
    }*/



    /**
     * @Route("/catalogueAll", name="catalogueAll")
     */
    public function afficheClientCatalogue(SerializerInterface $serializer)
    {

        $product = $this->getDoctrine()->getManager()->getRepository(Catalogue::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        //$formatted = $serializer->normalize($product);
        // return new JsonResponse($formatted);
        return $this->json($product, Response::HTTP_OK, [], [
            ObjectNormalizer::IGNORED_ATTRIBUTES => [

                'categories'
            ],
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            }
        ]);
    }

    /**
     * 
     * @Route ("/newCatalogue/{nom}/{description}" , name="CatalogueAdd")
     */
    public function CatalogueAdd(Request $request, SerializerInterface $serializer, EntityManagerInterface $em, $nom, $description)
    {
        //$content=$request->getContent();
        // $em=$this->getDoctrine()->getManager();
        $cata = new Catalogue();
        $cata->setNom($nom);
        $cata->setDescription($description);
        // $data=$serializer->deserialize($content,Catalogue::class,'json');
        $em->persist($cata);
        $em->flush();
        return new Response('catalogue added');
    }

    /**
     * @Route("/deleteCatalogue", name="deleteCatalogue")
     *
     */

    public function deleteCatalogueAction(Request $request)
    {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $catalogue = $em->getRepository(Catalogue::class)->find($id);
        if ($catalogue != null) {
            $em->remove($catalogue);
            $em->flush();

            $serialize = new Serializer([new ObjectNormalizer()]);
            $formatted = $serialize->normalize("catalogue a ete supprimee avec success.");
            return new JsonResponse($formatted);
        }
        return new JsonResponse("id catalogue invalide.");
    }
    /**
     * @Route("/updateCatalogue", name="updateCatalogue" )
     *
     */
    public function modifierCatalogueAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $catalogue = $this->getDoctrine()->getManager()
            ->getRepository(Catalogue::class)
            ->find($request->get("id"));
        $catalogue->setNom($request->get("nom"));
        $catalogue->setDescription($request->get("description"));


        $em->persist($catalogue);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($catalogue);
        return new JsonResponse("Catalogue a ete modifiee avec success.");
    }
    //supprimer catalogue
    /**
     * @Route("/CatalogueDelete/{id}",name="CatalogueDelete")
     */
    /* public function deletes($id, CatalogueRepository $rep)
    {
        $catalogue = $rep->find($id);
        $ar = $this->getDoctrine()->getManager();
        $ar->remove($catalogue);
        $ar->flush();
        return  $this->redirectToRoute("affichesCatalogueAdmin");
    }*/




    //update catalogue
    /**
     * @Route("catalogues/Updates/{id}", name="Updates")
     */
    /*function Updates(CatalogueRepository $repository, $id, Request $request)
    {
        $catalogue = $repository->find($id);
        $form = $this->createForm(CatalogueType::class, $catalogue);
        $form->add('Update', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('affichesCatalogueAdmin');
        }
        return $this->render('admin/catalogue/update.html.twig', [
            'form' => $form->createView()
        ]);
    }
*/
}
