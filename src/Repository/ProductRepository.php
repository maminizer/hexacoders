<?php

namespace App\Repository;

use App\Entity\Product;
use App\Data\FiltreData;
use App\Data\SearchData;
use Doctrine\ORM\QueryBuilder;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;

/**
 * @method Product|null find($id, $lockMode = null, $lockVersion = null)
 * @method Product|null findOneBy(array $criteria, array $orderBy = null)
 * @method Product[]    findAll()
 * @method Product[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProductRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Product::class);
    }

    // /**
    //  * @return Product[] Returns an array of Product objects
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
    public function findOneBySomeField($value): ?Product
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */

    public function findProduitByNom($title){
        return
            $this->createQueryBuilder('product')
                ->where('product.title LIKE :title OR product.id LIKE :title')
                ->setParameter('title', '%'.$title.'%')
                ->getQuery() ->getResult();
    }

    public function findByFiltre(FiltreData $filtreData)
    {
        $query = $this
            ->createQueryBuilder('produit');

        if(!empty($filtreData->enVente)){
            $query = $query
                ->andWhere('produit.enVente = 1');
        }
        if(!empty($filtreData->min)){
            $query = $query
                ->andWhere('produit.price >= :min')
                ->setParameter('min', $filtreData->min);
        }
        if(!empty($filtreData->max)){
            $query = $query
                ->andWhere('produit.price <= :max')
                ->setParameter('max', $filtreData->max);
        }

        return
            $query->getQuery()->getResult();
    }

    public function findsearch(SearchData $search):array
    {
        $query = $this
                 ->createQueryBuilder('p')
                 ->select('c', 'p')
                 ->join('p.categorie', 'c');

        if(!empty($search->q)){
            $query = $query
                 ->andwhere('p.title Like :q')
                 ->setParameter('q', "%{$search->q}%");
        }
        if(!empty($search->min)){
            $query = $query
                ->andwhere('p.price >= :min')
                ->setParameter('min', $search->min);
            } 
        
        if(!empty($search->max)){
            $query = $query
                ->andwhere('p.price <= :max')
                ->setParameter('max', $search->max);
            }
        
        if(!empty($search->categories)){
            $query = $query
                ->andwhere('c.id IN (:categories)')
                ->setParameter('categories', $search->categories);
            } 
        return $query->getQuery()->getResult();

    }
  
       /**
     * Récupère le prix minimum et maximum correspondant à une recherche
     * @return integer[]
     */
    public function findMinMax(SearchData $search): array
    {
        $results = $this->getSearchQuery($search, true)
            ->select('MIN(p.price) as min', 'MAX(p.price) as max')
            ->getQuery()
            ->getScalarResult();
        return [(int)$results[0]['min'], (int)$results[0]['max']];
    }




    private function getSearchQuery(SearchData $search, $ignorePrice = false): QueryBuilder
    {
        $query = $this
            ->createQueryBuilder('p')
            ->select('c', 'p')
            ->join('p.categorie', 'c');

        if (!empty($search->q)) {
            $query = $query
                ->andWhere('p.title LIKE :q')
                ->setParameter('q', "%{$search->q}%");
        }

        if (!empty($search->min) && $ignorePrice === false) {
            $query = $query
                ->andWhere('p.price >= :min')
                ->setParameter('min', $search->min);
        }

        if (!empty($search->max) && $ignorePrice === false) {
            $query = $query
                ->andWhere('p.price <= :max')
                ->setParameter('max', $search->max);
        }

        if (!empty($search->promo)) {
            $query = $query
                ->andWhere('p.promo = 1');
        }

        if (!empty($search->categories)) {
            $query = $query
                ->andWhere('c.id IN (:categories)')
                ->setParameter('categories', $search->categories);
        }

        return $query;
    }
}