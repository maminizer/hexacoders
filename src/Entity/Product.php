<?php

namespace App\Entity;

use App\Repository\ProductRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use phpDocumentor\Reflection\Types\Boolean;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ProductRepository::class)
 */
class Product
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     *
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * 
     */
    private $title;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="merci de choisir l'image du produit adequate")
     * @Assert\File(mimeTypes={"image/jpeg","image/gif","image/png"})
     * 
     */
    private $image;

    /**
     * @ORM\Column(type="float")
     * 
     */
    private $price;


  

    
    /**
     * @ORM\Column(type="boolean")
     * 
     */
    private $enVente;

    /**
     * @ORM\Column(type="integer")
     * 
     */
    private $quantity;

    /**
     * @ORM\Column(type="string", length=255)
     * 
     */
    private $description;



    /**
     * @ORM\ManyToOne(targetEntity=Categorie::class, inversedBy="product")
     */
    private $categorie;

    /**
     * @ORM\ManyToMany(targetEntity=Coffrets::class, mappedBy="productCoffrets")
     */
    private $ProductCoffrets;
    


    public function __construct()
    {
        $this->paniers = new ArrayCollection();
        $this->likes = new ArrayCollection();
        $this->items = new ArrayCollection();
        $this->commentaires = new ArrayCollection();
        $this->ProductCoffrets = new ArrayCollection();
    }



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): self
    {
        $this->title = $title;

        return $this;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getPrice(): ?float
    {
        return $this->price;
    }

    public function setPrice(float $price): self
    {
        $this->price = $price;

        return $this;
    }


    /**
     * @return Collection|Productlike[]
     */
   /* public function getLikes(): Collection
    {
        return $this->likes;
    }

    public function addLike(Productlike $like): self
    {
        if (!$this->likes->contains($like)) {
            $this->likes[] = $like;
            $like->setProduct($this);
        }

        return $this;
    }

    public function removeLike(Productlike $like): self
    {
        if ($this->likes->removeElement($like)) {
            // set the owning side to null (unless already changed)
            if ($like->getProduct() === $this) {
                $like->setProduct(null);
            }
        }

        return $this;
    }*/

    /**
     * permet de savoir si ce produit est liké par un user
     * @param User $user
     * @return bool
     */
    /*public function isLikedByUser(User $user): bool
    {
        foreach ($this->likes as $like) {
            if ($like->getUser() === $user) return true;
        }
        return false;
    }

    /**
     * @return Collection|Item[]
     */
   /* public function getItems(): Collection
    {
        return $this->items;
    }

    public function addItem(Item $item): self
    {
        if (!$this->items->contains($item)) {
            $this->items[] = $item;
            $item->setProduct($this);
        }

        return $this;
    }

    public function removeItem(Item $item): self
    {
        if ($this->items->removeElement($item)) {
            // set the owning side to null (unless already changed)
            if ($item->getProduct() === $this) {
                $item->setProduct(null);
            }
        }

        return $this;
    }
    */

    public function getEnVente(): ?bool
    {
        return $this->enVente;
    }

    public function setEnVente(bool $enVente): self
    {
        $this->enVente = $enVente;

        return $this;
    }

    public function getQuantity(): ?int
    {
        return $this->quantity;
    }

    public function setQuantity(int $quantity): self
    {
        $this->quantity = $quantity;

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
     * @return Collection|Commentaire[]
     */
   /* public function getCommentaires(): Collection
    {
        return $this->commentaires;
    }

    public function addCommentaire(Commentaire $commentaire): self
    {
        if (!$this->commentaires->contains($commentaire)) {
            $this->commentaires[] = $commentaire;
            $commentaire->setProduit($this);
        }

        return $this;
    }

    public function removeCommentaire(Commentaire $commentaire): self
    {
        if ($this->commentaires->removeElement($commentaire)) {
            // set the owning side to null (unless already changed)
            if ($commentaire->getProduit() === $this) {
                $commentaire->setProduit(null);
            }
        }

        return $this;
    }
    /**
     * @param User $user
     * @return bool
     */
 /*   public function isCommentedByUser(User $user): bool
    {
        foreach ($this->commentaires as $commentaire) {
            if ($commentaire->getUser() === $user) return true;
        }
        return false;
    }
*/
    public function getCategorie(): ?Categorie
    {
        return $this->categorie;
    }

    public function setCategorie(?Categorie $categorie): self
    {
        $this->categorie = $categorie;

        return $this;
    }

    /**
     * @return Collection|Coffrets[]
     */
    /*public function getProductCoffrets(): Collection
    {
        return $this->ProductCoffrets;
    }

    public function addProductCoffret(Coffrets $productCoffret): self
    {
        if (!$this->ProductCoffrets->contains($productCoffret)) {
            $this->ProductCoffrets[] = $productCoffret;
            $productCoffret->addProductCoffret($this);
        }

        return $this;
    }

    public function removeProductCoffret(Coffrets $productCoffret): self
    {
        if ($this->ProductCoffrets->removeElement($productCoffret)) {
            $productCoffret->removeProductCoffret($this);
        }

        return $this;
    }*/
    public function __toString()
    {
        return (string) $this->getTitle();
    }
}
