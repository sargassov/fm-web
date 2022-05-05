angular.module('players', ['ngStorage']).controller('playersController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    // $scope.loadPlayers = function () {
    //     $http.get(contextPath + '/team/players')
    //         .then(function successCallback(response) {
    //             $scope.Players = response.data;
    //         }, function errorCallback(response) {
    //             alert('PLAYERS OF YOUR TEAM NOT FOUND');
    //         });
    // };

    $scope.loadPlayersSort = function (parameter) {
        $http.get(contextPath + '/team/players/' + parameter)
            .then(function successCallback(response) {
                $scope.Players = response.data;
            }, function errorCallback(response) {
                alert('PLAYERS OF YOUR TEAM NOT FOUND');
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


    $scope.getActualDate()
    $scope.loadPlayersSort(0);
    $scope.getUserTeamName();
});