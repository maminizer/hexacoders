<?php

namespace App\Repository;

use App\Entity\Coffrets;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Coffrets|null find($id, $lockMode = null, $lockVersion = null)
 * @method Coffrets|null findOneBy(array $criteria, array $orderBy = null)
 * @method Coffrets[]    findAll()
 * @method Coffrets[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CoffretsRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Coffrets::class);
    }

    // /**
    //  * @return Coffrets[] Returns an array of Coffrets objects
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
    public function findOneBySomeField($value): ?Coffrets
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
    public function OrderByNom()
    {
        $em = $this->getEntityManager();
        $query = $em->createQuery('select s from App\Entity\Coffrets s order by s.nom ASC');
        return $query->getResult();
    }
}
