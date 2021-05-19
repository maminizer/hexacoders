<?php

namespace App\Repository;

use App\Entity\CategorieFavories;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method CategorieFavories|null find($id, $lockMode = null, $lockVersion = null)
 * @method CategorieFavories|null findOneBy(array $criteria, array $orderBy = null)
 * @method CategorieFavories[]    findAll()
 * @method CategorieFavories[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CategorieFavoriesRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, CategorieFavories::class);
    }

    // /**
    //  * @return CategorieFavories[] Returns an array of CategorieFavories objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('c.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?CategorieFavories
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
