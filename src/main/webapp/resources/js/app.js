


var myApp = angular.module('myApp', []);
var root = "/webres2016/";

myApp.controller('timesController', function ($scope, $http) {

    $scope.selectedTime = undefined;
    $scope.id =  getAttributeValue('availability-times-for-service');
   

    $http.get(root + "resources/services/" + $scope.id + "/availabilityTimes")
            .then(function (response) {
                $scope.times = response.data;

            });


    $scope.select = function (time) {
        $scope.selectedTime = time;
    };

    $scope.confirm = function ($event) {
        $http.post(root + "resources/customers/availabilityTime", $scope.selectedTime)
            .then(function (response) {
                console.log(response);
                document.location = root + 'secure/payment/payment.html';
            });
    };

});





myApp.directive('availabilityTimesForService', function(){
   return {
      templateUrl : root + 'javax.faces.resource/js/templates/availabilityTimes.tpl.html'
   };
});


var getAttributeValue = function(attribute){
    return document.querySelectorAll('[' + attribute + ']')[0].getAttribute(attribute);
};