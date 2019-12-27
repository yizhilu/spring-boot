//配置
var app = angular.module('app').config(
		[ '$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
				function($controllerProvider, $compileProvider, $filterProvider, $provide) {
					// 懒加载 控制器，指令和服务
					app.controller = $controllerProvider.register;
					app.directive = $compileProvider.directive;
					app.filter = $filterProvider.register;
					app.factory = $provide.factory;
					app.service = $provide.service;
					app.constant = $provide.constant;
					app.value = $provide.value;
				} ]).config([ '$translateProvider', function($translateProvider) {
	$translateProvider.preferredLanguage('en');
	$translateProvider.useLocalStorage();
} ]);