$.noConflict();
$( document ).ready(function() {
    
   $('[data-show-payment-overlay]').click(function(event){
       $('.payment-overlay').show();
   });
});