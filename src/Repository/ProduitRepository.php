<?php

namespace App\Repository;

use App\Data\FiltreData;
use App\Data\SearchData;
use App\Entity\Produit;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Knp\Component\Pager\Pagination\PaginationInterface;
use Knp\Component\Pager\PaginatorInterface;

/**
 * @method Produit|null find($id, $lockMode = null, $lockVersion = null)
 * @method Produit|null findOneBy(array $criteria, array $orderBy = null)
 * @method Produit[]    findAll()
 * @method Produit[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProduitRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry, PaginatorInterface $paginator)
    {
        parent::__construct($registry, Produit::class);
        $this->paginator = $paginator;
    }

    /**
     * Recupere les produits qui ont un lien avec une recherche
     * @return PaginationInterface
     */
    public function findSearch(SearchData $search): PaginationInterface
    {
        //kibch naaml categories wnhb nrecuperi les categories fl filtre
         $query = $this
                    ->createQueryBuilder('p');
        //        return $query->getQuery()->getResult();
       if(!empty($search->q)){
           $query = $query
               -> andwhere('p.nom LIKE :q')
               ->setParameter('q', "%{$search->q}%");
       }

        $query = $query->getQuery();
       return $this->paginator->paginate(
           $query,
           $search->page,
           7
       );
    }
    public function findByFiltre(FiltreData $filtreData): PaginationInterface
    {
        $query = $this
            ->createQueryBuilder('produit');

        if(!empty($filtreData->EnVente)){
            $query = $query
                ->andWhere('produit.EnVente = 1');
        }
        if(!empty($filtreData->min)){
            $query = $query
                ->andWhere('produit.prix >= :min')
                ->setParameter('min', $filtreData->min);
        }
        if(!empty($filtreData->max)){
            $query = $query
                ->andWhere('produit.prix <= :max')
                ->setParameter('max', $filtreData->max);
        }
        $query = $query->getQuery();
        return $this->paginator->paginate(
            $query,
            $filtreData->page,
            7
        );
    }

    public function findProduitByNom($nom){
        return
            $this->createQueryBuilder('produit')
                 ->where('produit.nom LIKE :nom')
                 ->setParameter('nom', '%'.$nom.'%')
                 ->getQuery() ->getResult();
    }
    // /**
    //  * @return Produit[] Returns an array of Produit objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('p.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Produit
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
