window.onload = () => {
    // Variables
    let stripe = Stripe('pk_test_51IV2PtABbOaXQW4MWFmrl24P9egXTxKX26EzmJDiq4UnaTSDLkWvnPSKMUgBU6f5Vuwo6Ji4DTxafKLqQnp1lyRK00FXCIbTD9')
    let elements = stripe.elements()
    let redirect = "/commande"


    // Objets de la page
    let cardHolderName = document.getElementById("cardholder-name")
    let cardButton = document.getElementById("card-button")
    let clientSecret = cardButton.dataset.secret;

    console.log(document.referrer);

    // Crée les éléments du formulaire de carte bancaire
    let card  = elements.create("card");
    card.mount("#card-elements")

    //gerer  la saisie
    // card.addEventListener("change", (event) => {
    //     let displayError = document.getElementById("card-errors")
    //     if(event.error){
    //         displayError.textContent = event.error.message;
    //     }else{
    //         displayError.textContent = "";
    //     }
    // })

    card.addEventListener("change", (event)=>{
        let displayError = document.getElementById("alert-container")
        if(event.error){
            displayError.innerHTML = "<div class=\"alert alert-danger\" role=\"alert\"\><strong\>\<div id=\"card-errors\" role=\"alert\">"+ event.error.message;+"\</div>\</strong\>\</div> "
        }
    })

    // on gere le paiement
    cardButton.addEventListener("click", () => {
        stripe.handleCardPayment(
            clientSecret, card, {
                payment_method_data: {
                    billing_details: {name: cardHolderName.value}
                }
            }
        ).then((result) => {
            if(result.error){
                document.getElementById("errors").innerText = result.error.message
            }else {
                document.location.href = redirect
            }
        })
    })
}
