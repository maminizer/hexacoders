<?php

namespace App\Entity;

use App\Repository\OffreRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use PhpParser\Node\Scalar\String_;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * @ORM\Entity(repositoryClass=OffreRepository::class)
 */
class Offre
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="Taux de reduction est obligatoire !! ")
     * @Assert\Range(
     *      min = 1,
     *      max = 100,
     *      notInRangeMessage=" le Taux de reduction doit entre {{ min }} et {{ max }} !! "
     * )
     */
    private $Taux_reduction;

    /**
     * @ORM\ManyToMany(targetEntity=Product::class, inversedBy="offres")
     */
    private $products;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $mail;

    public function __construct()
    {
        $this->offre = new ArrayCollection();
        $this->produits = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPrix(): ?int
    {
        return $this->prix;
    }

    public function getDescription(): ?String
    {
        return $this->description;
    }
    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function getQuantite(): ?int
    {
        return $this->Quantite;
    }

    public function getTaux_Reduction(): ?int
    {
        return $this->Taux_reduction;
    }
    public function getTauxReduction(): ?int
    {
        return $this->Taux_reduction;
    }
    public function setTaux_Reduction(int $Taux_reduction): self
    {
        $this->Taux_reduction = $Taux_reduction;

        return $this;
    }
    public function setTauxReduction(int $Taux_reduction): self
    {
        $this->Taux_reduction = $Taux_reduction;

        return $this;
    }

    /**
     * @return Collection|Product[]
     */
    public function getProducts(): Collection
    {
        return $this->produits;
    }

    public function addProduct(Product $produit): self
    {
        if (!$this->produits->contains($produit)) {
            $this->produits[] = $produit;
        }

        return $this;
    }

    public function removeProduct(Product $produit): self
    {
        $this->produits->removeElement($produit);

        return $this;
    }

    public function getmail(): ?string
    {
        return $this->mail;
    }

    public function setmail(string $mail): self
    {
        $this->mail = $mail;

        return $this;
    }


}
