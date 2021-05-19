<?php

namespace App\Controller;

use App\Entity\Catalogue;
use App\Form\CatalogueType;
use App\Repository\CatalogueRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class CatalogueController extends AbstractController
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

    //afficher catalogue
    /**
     * @Route("/admin/affichesAdminCatalogue",name="affichesCatalogueAdmin")
     */
    public function affichesCatalogueAdmin(CatalogueRepository $rep)
    {
        $da = $rep->OrderByNameDQL();
        return $this->render('admin/catalogue/afficheAdmin.html.twig', ['data' => $da]);
    }
    //afficher les catalogue
    /**
     * @Route("/collection",name="afficheClientCatalogue")
     */
    public function afficheClientCatalogue()
    {
        $repo = $this->getDoctrine()->getRepository(Catalogue::class);
        $da = $repo->findAll();
        return $this->render('catalogue/index.html.twig', ['catalogu' => $da]);
    }

    //supprimer catalogue
    /**
     * @Route("/CatalogueDelete/{id}",name="CatalogueDelete")
     */
    public function deletes($id, CatalogueRepository $rep)
    {
        $catalogue = $rep->find($id);
        $ar = $this->getDoctrine()->getManager();
        $ar->remove($catalogue);
        $ar->flush();
        return  $this->redirectToRoute("affichesCatalogueAdmin");
    }


    /**
     * @param Request $request
     * @return Response
     * @Route ("/CatalogueAdd" , name="CatalogueAdd")
     */
    public function CatalogueAdd(Request $request)
    {
        $catalogue = new Catalogue();
        $form = $this->createForm(CatalogueType::class, $catalogue);
        $form->add('Ajouter', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();

            $em->persist($catalogue);
            $em->flush();
            return $this->redirectToRoute('affichesCatalogueAdmin');
        }
        return $this->render('admin/catalogue/add.html.twig', [
            'form' => $form->createView(), 'editMode' => $catalogue->getId() !== null
        ]);
    }

    //update catalogue
    /**
     * @Route("catalogues/Updates/{id}", name="Updates")
     */
    function Updates(CatalogueRepository $repository, $id, Request $request)
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

    /**
  
     * @Route("affichesAdmin/searchCataloguex",name="searchCataloguex")
     */
    public function searchCataloguex(Request $req, CatalogueRepository $rep)
    {
        $catalogue = '';
        $requestString = $req->get('searchValue');
        if (strlen($requestString) > 0) {
            $catalogue = $rep->findCatalogueByNom($requestString);
        } else {
            $catalogue = $rep->findAll();
        }
        $encoders = [new XmlEncoder(), new JsonEncoder()];
        $normalizers = [new ObjectNormalizer()];

        $serializer = new Serializer($normalizers, $encoders);
        $jsonContent = $serializer->serialize($catalogue, 'json', ['ignored_attributes' => [
            'categories'

        ]]);

        $response = new Response(json_encode($jsonContent));
        $response->headers->set('Content-Type', 'application/json; charset=utf-8');
        return $response;
    }

   
    public function megaMenu(CatalogueRepository $catalogueRepository){

        $catalogues = $catalogueRepository->findAll([]);

        
        return $this->render('components/megamenu.html.twig',[
            'catalogues' => $catalogues
        ]);

    }
}


