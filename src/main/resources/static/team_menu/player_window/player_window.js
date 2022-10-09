angular.module('player_window', ['ngStorage']).controller('player_windowController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.loadRootPlayer = function () {
        $scope.player = $localStorage.rootPlayer;
    };

    $scope.getAnotherPlayerPlus = function (number) {
        $http.get(contextPath + '/player/vision/next/' + number)
            .then(function successCallback(response) {
                $scope.player = response.data;
            }, function errorCallback(response) {
                alert('PLAYER NOT FOUND');
            });
    };

    $scope.getAnotherPlayerMinus = function (number) {
        $http.get(contextPath + '/player/vision/prev/' + number)
            .then(function successCallback(response) {
                $scope.player = response.data;
            }, function errorCallback(response) {
                alert('PLAYER NOT FOUND');
            });
    };

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback(response) {
                alert('PRESENT DAY NOT FOUND');
            });
    };



    $scope.loadRootPlayer();
    $scope.getActualDate();
});