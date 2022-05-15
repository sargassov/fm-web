angular.module('coaches', ['ngStorage']).controller('coachesController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadCoaches = function () {
        $http.get(contextPath + '/coach/all')
            .then(function successCallback(response) {
                $scope.Coaches = response.data;
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

    $scope.addNewCoach = function () {
        $http.get(contextPath + '/coach/new/responce')
            .then(function successCallback(response) {
                window.location.href = '../add_new_coach/add_new_coach.html';
            }, function errorCallback(response) {
                alert('YOU CANT ADD ONE MORE COACH')
            });
    };

    $scope.setCoachFree = function (count) {
        $http.delete(contextPath + '/coach/delete/' + count)
            .then(function successCallback(response) {
                $scope.loadCoaches();
            }, function errorCallback(response) {
                alert('YOU CANT DELETE THIS COACH')
            });
    };

    $scope.setAnotherPlayer = function (coach) {
        $http.put(contextPath + '/coach/player/change', coach)
            .then(function successCallback(response) {
                $scope.loadCoaches();
            }, function errorCallback(response) {
                alert('PLAYERS WITH NAME ' + name + ' NOT FOUND');
            });
    };


    $scope.getActualDate();
    $scope.loadCoaches();
    $scope.getUserTeamName();
});