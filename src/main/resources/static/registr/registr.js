angular.module('registr', ['ngStorage']).controller('registrController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.createNewGame = function () {
        $http.post(contextPath + '/new_game', $scope.user)
            .then(function successCallback(response) {
                window.location.href = '../main_menu/main_menu.html';
            }, function errorCallback(response) {
                alert('NEW GAME WAS NOT CREATED');
            });
    };
});