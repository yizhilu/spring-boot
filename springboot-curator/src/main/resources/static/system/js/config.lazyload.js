// 懒加载配置
angular.module('app')
  /**
    * jQuery插件配置使用ui-jq指令，配置所需的js和css文件
    * key：jQuery插件的函数名称
    * value：位于css js文件的数组
   */
  .constant('JQ_CONFIG', {
      easyPieChart:   ['lib/jquery/charts/easypiechart/jquery.easy-pie-chart.js'],
      sparkline:      ['lib/jquery/charts/sparkline/jquery.sparkline.min.js'],
      plot:           ['lib/jquery/charts/flot/jquery.flot.min.js', 
                          'lib/jquery/charts/flot/jquery.flot.resize.js',
                          'lib/jquery/charts/flot/jquery.flot.tooltip.min.js',
                          'lib/jquery/charts/flot/jquery.flot.spline.js',
                          'lib/jquery/charts/flot/jquery.flot.orderBars.js',
                          'lib/jquery/charts/flot/jquery.flot.pie.min.js'],
      slimScroll:     ['lib/jquery/slimscroll/jquery.slimscroll.min.js'],
      sortable:       ['lib/jquery/sortable/jquery.sortable.js'],
      nestable:       ['lib/jquery/nestable/jquery.nestable.js',
                          'lib/jquery/nestable/nestable.css'],
      filestyle:      ['lib/jquery/file/bootstrap-filestyle.min.js'],
      slider:         ['lib/jquery/slider/bootstrap-slider.js',
                          'lib/jquery/slider/slider.css'],
      chosen:         ['lib/jquery/chosen/chosen.jquery.min.js',
                          'lib/jquery/chosen/chosen.css'],
      TouchSpin:      ['lib/jquery/spinner/jquery.bootstrap-touchspin.min.js',
                          'lib/jquery/spinner/jquery.bootstrap-touchspin.css'],
      wysiwyg:        ['lib/jquery/wysiwyg/bootstrap-wysiwyg.js',
                          'lib/jquery/wysiwyg/jquery.hotkeys.js'],
      dataTable:      ['lib/jquery/datatables/jquery.dataTables.min.js',
                          'lib/jquery/datatables/dataTables.bootstrap.js',
                          'lib/jquery/datatables/dataTables.bootstrap.css'],
      vectorMap:      ['lib/jquery/jvectormap/jquery-jvectormap.min.js', 
                          'lib/jquery/jvectormap/jquery-jvectormap-world-mill-en.js',
                          'lib/jquery/jvectormap/jquery-jvectormap-us-aea-en.js',
                          'lib/jquery/jvectormap/jquery-jvectormap.css'],
      footable:       ['lib/jquery/footable/footable.all.min.js',
                          'lib/jquery/footable/footable.core.css']
      }
  )
  // oclazyload config
  .config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
      // We configure ocLazyLoad to use the lib script.js as the async loader
      $ocLazyLoadProvider.config({
          debug:  false,
          events: true,
          modules: [
              {
                  name: 'ngGrid',
                  files: [
                      'lib/modules/ng-grid/ng-grid.min.js',
                      'lib/modules/ng-grid/ng-grid.min.css',
                      'lib/modules/ng-grid/theme.css'
                  ]
              },
              {
                  name: 'ui.select',
                  files: [
                      'lib/modules/angular-ui-select/select.min.js',
                      'lib/modules/angular-ui-select/select.min.css'
                  ]
              },
              {
                  name:'angularFileUpload',
                  files: [
                    'lib/modules/angular-file-upload/angular-file-upload.min.js'
                  ]
              },
              {
                  name:'ui.calendar',
                  files: ['lib/modules/angular-ui-calendar/calendar.js']
              },
              {
                  name: 'ngImgCrop',
                  files: [
                      'lib/modules/ngImgCrop/ng-img-crop.js',
                      'lib/modules/ngImgCrop/ng-img-crop.css'
                  ]
              },
              {
                  name: 'angularBootstrapNavTree',
                  files: [
                      'lib/modules/angular-bootstrap-nav-tree/abn_tree_directive.js',
                      'lib/modules/angular-bootstrap-nav-tree/abn_tree.css'
                  ]
              },
              {
                  name: 'toaster',
                  files: [
                      'lib/modules/angularjs-toaster/toaster.js',
                      'lib/modules/angularjs-toaster/toaster.css'
                  ]
              },
              {
                  name: 'textAngular',
                  files: [
                      'lib/modules/textAngular/textAngular-sanitize.min.js',
                      'lib/modules/textAngular/textAngular.min.js'
                  ]
              },
              {
                  name: 'vr.directives.slider',
                  files: [
                      'lib/modules/angular-slider/angular-slider.min.js',
                      'lib/modules/angular-slider/angular-slider.css'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular',
                  files: [
                      'lib/modules/videogular/videogular.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.controls',
                  files: [
                      'lib/modules/videogular/plugins/controls.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.buffering',
                  files: [
                      'lib/modules/videogular/plugins/buffering.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.overlayplay',
                  files: [
                      'lib/modules/videogular/plugins/overlay-play.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.poster',
                  files: [
                      'lib/modules/videogular/plugins/poster.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.imaads',
                  files: [
                       'lib/modules/videogular/plugins/ima-ads.min.js'
                  ]
              }
          ]
      });
  }])
;