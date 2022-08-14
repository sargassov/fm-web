angular.module('visual_calendar', ['ngStorage']).controller('visual_calendarController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.loadMonth = function (parameter) {
        $http.get(contextPath + '/calendar/month/' + parameter)
            .then(function successCallback(response) {
                $scope.getNameOfMonth(parameter);
                $scope.events = response.data;
                if(parameter < 0)
                    parameter = 10
                if(parameter > 10)
                    parameter = 0;
                $scope.eventsCoeff = parameter;
            }, function errorCallback(response) {
                alert('CANT LOAD CALENDAR');
            });
    };

    $scope.getParameter = function () {
        $http.get(contextPath + '/calendar/month/parameter')
            .then(function successCallback(response) {
                $scope.parameter = response.data.parameter;
                $scope.loadMonth($scope.parameter);
            }, function errorCallback(response) {
                alert('CANT LOAD MONTH PARAMETER');
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

    $scope.getNameOfMonth = function (parameter) {
        $http.get(contextPath + '/calendar/month/' + parameter + '/name')
            .then(function successCallback(response) {
                $scope.monthName = response.data;
            }, function errorCallback(response) {
                alert('NAME OF MONTH NOT FOUND');
            });
    };



    $scope.getActualDate();
    $scope.getParameter();
    $scope.getUserTeamName();
});