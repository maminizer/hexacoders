<?php

namespace App\Entity;

use App\Repository\CatalogueRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=CatalogueRepository::class)
 * 
 */
class Catalogue
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     *  @Groups("data")
     */
    private $id;

    /**
     * 
     * @ORM\Column(type="string", length=255)
     *  @Groups("data")
     */
    private $nom;

    /**
     * @ORM\Column(type="string", length=255)
     *
     *  @Groups("data")
     */
    private $description;

    /**
     * @ORM\OneToMany(targetEntity=Categorie::class, mappedBy="catalogue",cascade={"all"},orphanRemoval=true)
     * 
     */
    private $categories;

    public function __construct()
    {
        $this->categories = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
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

    /**
     * @return Collection|Categorie[]
     */
    public function getCategories(): Collection
    {
        return $this->categories;
    }

    public function addCategory(Categorie $category): self
    {
        if (!$this->categories->contains($category)) {
            $this->categories[] = $category;
            $category->setCatalogue($this);
        }

        return $this;
    }

    public function removeCategory(Categorie $category): self
    {
        if ($this->categories->removeElement($category)) {
            // set the owning side to null (unless already changed)
            if ($category->getCatalogue() === $this) {
                $category->setCatalogue(null);
            }
        }

        return $this;
    }
    public function __toString()
    {
        return (string) $this->getNom();
    }
}
