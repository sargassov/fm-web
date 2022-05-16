angular.module('training_programs', ['ngStorage']).controller('training_programsController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadCoaches = function () {
        $http.get(contextPath + '/coach/all')
            .then(function successCallback(response) {
                $scope.Coaches = response.data;
            }, function errorCallback(response) {
                alert('COACH PROGRAMS OF YOUR TEAM NOT FOUND');
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

    $scope.changeProgram = function (count, progrId) {
        $http.put(contextPath + '/coach/program/' + count + '/' + progrId)
            .then(function successCallback(response) {
                $scope.loadCoaches();
            }, function errorCallback(response) {
                alert('COACH PROGRAM CHANGE FAILURE')
            });
    };


    $scope.getActualDate();
    $scope.loadCoaches();
    $scope.getUserTeamName();
});