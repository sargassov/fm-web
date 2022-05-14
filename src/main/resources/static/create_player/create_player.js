angular.module('create_player', ['ngStorage']).controller('create_playerController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.createNewPlayer = function () {
        $http.post(contextPath + '/player/new/create', $scope.player)
            .then(function successCallback(response) {
                window.location.href = '../create_player/create_player.html';
                $scope.getUserTeamName();
            }, function errorCallback(response) {
                alert('NEW PLAYER WAS NOT CREATED');
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

    $scope.getUserTeamName = function () {
        $http.get(contextPath + '/team/name')
            .then(function successCallback(response) {
                $scope.team = response.data;
            }, function errorCallback(response) {
                alert('TEAM NAME NOT FOUND');
            });
    };

    $scope.guessCost = function (){
        $http.post(contextPath + '/player/new/cost', $scope.player)
            .then(function successCallback(response) {
                $scope.cost = response.data;
                $scope.getUserTeamName();
            }, function errorCallback(response) {
                alert('CANT GET PLAYERS COST');
            });
    }

    $scope.getActualDate();
    $scope.getUserTeamName();
});