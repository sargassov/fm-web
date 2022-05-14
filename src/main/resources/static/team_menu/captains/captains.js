angular.module('captains', ['ngStorage']).controller('captainsController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


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

    $scope.setToCaptain = function (name) {
        $http.put(contextPath + '/team/captain', name)
            .then(function successCallback(responce) {
                $scope.loadPlayersSort(9)
            }, function errorCallback(responce) {
                alert('TEAM NAME NOT FOUND');
            });
    };


    $scope.getActualDate();
    $scope.loadPlayersSort(9);
    $scope.getUserTeamName();
});