'use strict';

var gulp = require('gulp');
var gulpif = require('gulp-if');/* if else */
var gulprename = require('gulp-rename');/*replace file name and ext*/
var code = require('gulp-code'); /*file zip*/
var less = require('gulp-less');/*compile less to css*/
var lazypipe = require('lazypipe');/*pipe child  , example: child thread*/
var babel = require('gulp-babel');/*es6 convert es5*/
var plumber = require('gulp-plumber');/*error handle*/
var del = require('del');/* Directory delete */
var path = require('path');/*file path*/
var autoprefixer = require('gulp-autoprefixer');/*h5 css auto prefix*/
var webpack = require('gulp-webpack'); /*web pack*/

var webpackConfig = require("./webpack.config.js");
gulp.task("webpack", function() { 
    return gulp 
        .src('./webpack.config.js') 
        .pipe(gulpif(!isBuild, plumber(err)))
        .pipe(webpack(webpackConfig)) 
        .pipe(gulp.dest('./res/js/webpack')); 
}); 


// many modules 不好处理
// gulp.task("webpack", function() { 
//     return gulp.src("./gulp.webpack.config.js")
//         .pipe(webpack())
//         .pipe(gulprename({
//             basename: "index",
//             prefix: "pack-",
//             extname: ".js"
//         }))
//         .pipe(gulp.dest('./res/js/webpack'));
// }); 







var isBuild = true;

var isType = function(type) {
  return function(file) {
    var extname = path.extname(file.path);
    return extname === '.' + type;
  };
};


function err(error) {
  console.error('[ERROR]'.red + error.message);
  this.emit('end');
}

gulp.task('clean', function (cb) {
  if(isBuild)
    del.sync(['build'], {
      force: true
    });
  cb();
});

gulp.task('css', ['clean'], function () {
  return gulp.src(['res/css/**/*.less', 'res/css/**/*.css'])
    .pipe(gulpif(!isBuild, plumber(err)))
    .pipe(gulpif(isType('less'), less({
      paths: [ path.join(__dirname) ],
      sourceMap: true,
      relativeUrls: true
    })))
    .pipe(gulpif(isBuild, autoprefixer()))
    .pipe(gulpif(isBuild, code.minify()))
    .pipe(gulp.dest('build/css'));
});



var jsxChannels = lazypipe()
    .pipe(babel)
    .pipe(gulprename, function(path) {
        path.extname = '.jsx.js';
    });



gulp.task('js', ['clean','webpack'], function () {
  console.log ("isBuild:"+isBuild)
  return gulp.src(['res/js/**/*.js', 'res/js/**/*.jsx'])
    .pipe(gulpif(!isBuild, plumber(err)))
    .pipe(gulpif(isType('jsx'), jsxChannels()))
    .pipe(gulpif(isBuild, code.minify()))
    .pipe(gulp.dest('build/js'));
});  



gulp.task('copy', ['clean'], function () {
  return gulp.src(
    [
      'res/**/*.png',
      'res/**/*.jpg',
      'res/**/*.jpeg',
      'res/**/*.gif',
      'res/**/*.html',
      'res/**/*.htm',
      'res/**/*.ttf',
      'res/**/*.eot',
      'res/**/*.svg',
      'res/**/*.woff'
    ])
    .pipe(gulp.dest('build/'));
});

gulp.task("project" , function (){
  del.sync(['sjht'], {
      force: true
  });
  gulp.src(['build/**/*']).pipe(gulp.dest("sjht/build"));
  gulp.src(['*.html']).pipe(gulp.dest("sjht"));
});



gulp.task('watch', ['default'], function () {
  isBuild = false;
  console.log ("watch isBuild:"+isBuild);
  gulp.watch(['res/js/**/*.js', 'res/js/**/*.jsx'], ['js']);
  gulp.watch(['res/css/**/*.less' , 'res/css/**/*.css'], ['css']);
});




gulp.task('default', ['clean', 'css' , 'js' , 'copy']);
