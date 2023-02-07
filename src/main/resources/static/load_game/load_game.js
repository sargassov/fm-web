angular.module('load_game', ['ngStorage']).controller('load_gameController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.createNewGame = function () {
        $http.post(contextPath + '/load_game', $scope.user)
            .then(function successCallback() {
                window.location.href = '../main_menu/main_menu.html';
            }, function errorCallback(response) {
                alert('NAME OF USER OR PASSWORD IS WRONG');
            });
    };

    $scope.back = function () {
        window.location.href = '../index.html';
    };
});