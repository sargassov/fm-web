angular.module('youth_academy', ['ngStorage']).controller('youth_academyController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.isUserAlreadyVisited() = function () {
        $http.get(contextPath + '/junior')
            .then(function successCallback(response) {
                $scope.loadRandomYoungPlayers();
            }, function errorCallback(response) {
                alert('YOU HAVE ALREADY VISITED THE ACADEMY TODAY');
            });
    }

    $scope.loadRandomYoungPlayers = function () {
        $http.get(contextPath + '/junior/all_five/')
            .then(function successCallback(response) {
                $scope.Players = response.data;
            }, function errorCallback(response) {
                alert('YOUNG PLAYERS CANT BE LOADED');
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


    $scope.isUserAlreadyVisited();
    $scope.getActualDate();
    $scope.getUserTeamName();
});