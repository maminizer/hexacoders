<?php

namespace App\Entity;

use App\Repository\CoffretsRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * @ORM\Entity(repositoryClass=CoffretsRepository::class)
 */
class Coffrets
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\ManyToMany(targetEntity=Product::class, inversedBy="ProductCoffrets")
     */
    private $productCoffrets;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Length(
     *      min = 2,
     *      max = 255,
     *      minMessage = "Your  nom must be at least {{ limit }} characters long",
     *      maxMessage = "Your  nom cannot be longer than {{ limit }} characters"
     * )
     * @Assert\NotNull
     */
    private $nom;
    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Please upload image")
     * @Assert\File(mimeTypes={"image/jpeg","image/gif","image/png"})
     *   
     * @Assert\File(mimeTypesMessage = "Please upload a valid PDF")

     * @Assert\NotNull
     */
    private $image;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Length(
     *      min = 2,
     *      max = 255,
     *      minMessage = "Your  description must be at least {{ limit }} characters long",
     *      maxMessage = "Your  description cannot be longer than {{ limit }} characters"
     * )
     * @Assert\NotNull
     */
    private $description;

    /**
     * @ORM\Column(type="float")
     *  @Assert\NotNull
     */
    private $prix;
    /**
     * @ORM\Column(type="float")
     * @Assert\NotNull
     */
    private $offre;




    public function __construct()
    {
        $this->productCoffrets = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    /**
     * @return Collection|Product[]
     */
    public function getProductCoffrets(): Collection
    {
        return $this->productCoffrets;
    }

    public function addProductCoffret(Product $productCoffret): self
    {
        if (!$this->productCoffrets->contains($productCoffret)) {
            $this->productCoffrets[] = $productCoffret;
        }

        return $this;
    }

    public function removeProductCoffret(Product $productCoffret): self
    {
        $this->productCoffrets->removeElement($productCoffret);

        return $this;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }
    public function getOffre(): ?string
    {
        return $this->offre;
    }

    public function setOffre(string $offre): self
    {
        $this->offre = $offre;

        return $this;
    }
    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }
    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }
}
