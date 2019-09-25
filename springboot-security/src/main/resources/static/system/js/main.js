'use strict';

/* Controllers */

angular.module('app').controller('AppCtrl',
		[ '$scope', '$translate', '$localStorage', '$window', function($scope, $translate, $localStorage, $window) {
			// 将'ie' class 添加到html中
			var isIE = !!navigator.userAgent.match(/MSIE/i);
			isIE && angular.element($window.document.body).addClass('ie');
			isSmartDevice($window) && angular.element($window.document.body).addClass('smart');

			// config
			$scope.app = {
				name : 'Angulr',
				version : '1.6.1',
				// for chart colors
				color : {
					primary : '#7266ba',
					info : '#23b7e5',
					success : '#27c24c',
					warning : '#fad733',
					danger : '#f05050',
					light : '#e8eff0',
					dark : '#3a3f51',
					black : '#1c2b36'
				},
				settings : {
					themeID : 1,
					navbarHeaderColor : 'bg-black',
					navbarCollapseColor : 'bg-white-only',
					asideColor : 'bg-black',
					headerFixed : true,
					asideFixed : false,
					asideFolded : false,
					asideDock : false,
					container : false
				}
			}

			// 保存设置到本地存储
			if (angular.isDefined($localStorage.settings)) {
				$scope.app.settings = $localStorage.settings;
			} else {
				$localStorage.settings = $scope.app.settings;
			}
			$scope.$watch('app.settings', function() {
				if ($scope.app.settings.asideDock && $scope.app.settings.asideFixed) {
					// 在dock和fixed旁边必须设置标题固定。
					$scope.app.settings.headerFixed = true;
				}
				// 保存到本地存储
				$localStorage.settings = $scope.app.settings;
			}, true);

			// angular translate
			$scope.lang = {
				isopen : false
			};
			$scope.langs = {
				en : 'English',
				zh : '简体中文',
			};
			$scope.selectLang = $scope.langs[$translate.proposedLanguage()] || "English";
			$scope.setLang = function(langKey, $event) {
				// 设置当前语言
				$scope.selectLang = $scope.langs[langKey];
				//可以在运行时更改语言
				$translate.use(langKey);
				$scope.lang.isopen = !$scope.lang.isopen;
			};

			function isSmartDevice($window) {
				var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
				//检查iOs，Android，Blackberry，Opera Mini和Windows
				return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
			}

		} ]);