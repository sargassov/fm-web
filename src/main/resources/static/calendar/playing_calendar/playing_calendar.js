angular.module('playing_calendar', ['ngStorage']).controller('playing_calendarController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.loadTour = function (parameter) {
        $http.get(contextPath + '/calendar/league_calendar/' + parameter)
            .then(function successCallback(response) {
                $scope.matchday = response.data;
                $scope.matches = response.data.matches;
            }, function errorCallback(response) {
                alert('TOURS IN CALENDAR NOT FOUND');
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

    $scope.getLeagueName = function () {
        $http.get(contextPath + '/league/name')
            .then(function successCallback(response) {
                $scope.league = response.data;
            }, function errorCallback(response) {
                alert('LEAGUE NAME NOT FOUND');
            });
    };


    $scope.getActualDate();
    $scope.loadTour(1);
    $scope.getLeagueName();
});