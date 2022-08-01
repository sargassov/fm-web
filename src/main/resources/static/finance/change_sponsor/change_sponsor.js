angular.module('change_sponsor', ['ngStorage']).controller('change_sponsorController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.sponsorRequest = function () {
        $http.get(contextPath + '/team/sponspor/start_message')
            .then(function successCallback(response) {
                $scope.header_message = response.data;
                if($scope.header_message.response.endsWith("list.")){
                    $http.get(contextPath + '/sponsor/all')
                        .then(function successCallback(response) {
                            $scope.Sponsors = response.data;
                        }, function errorCallback(response) {
                            alert('SPONSOR LIST NOT LOADED');
                        });
                }
            }, function errorCallback(response) {
                $scope.header_message = response.data;
                // alert('HEADER MESSAGE NOT LOADED');
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

    $scope.changeSponsor = function (sponsor) {
        $http.post(contextPath + '/sponspor/change', sponsor)
            .then(function successCallback(response) {
                window.location.reload();
            }, function errorCallback(response) {
                alert('SPONSOR WAS NOT CHANGED');
            });
    };


    $scope.sponsorRequest();
    $scope.getActualDate();
    $scope.getUserTeamName();
});