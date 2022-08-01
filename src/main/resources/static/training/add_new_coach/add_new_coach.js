angular.module('add_new_coach', ['ngStorage']).controller('add_new_coachController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.addNewCoach = function () {
        $http.post(contextPath + '/coach/new/create', $scope.coach)
            .then(function successCallback() {
                window.history.back();
                window.location.reload();
                // window.location.href('../coaches/coaches.html');
            }, function errorCallback() {
                alert('NEW COACH WAS NOT ADD');
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

    $scope.guessCost = function () {
        $http.post(contextPath + '/coach/new/cost', $scope.coach)
            .then(function successCallback(response) {
                $scope.cost = response.data;
                $scope.getUserTeamName();
            }, function errorCallback(response) {
                alert('CANT GET COACH COST');
            });
    }

    $scope.getActualDate();
    $scope.getUserTeamName();
});