/**
 * Created by Feil.Wang on 2015/7/28.
 */
var gulp = require('gulp'),
    minifycss = require('gulp-minify-css'),
    jshint = require('gulp-jshint'),
    uglify = require('gulp-uglify'),
    rename = require('gulp-rename'),
    concat = require('gulp-concat'),
    notify = require('gulp-notify'),
    del = require('del');

/**教师个人主页*/
gulp.task('concat-js-td-base', function () {
    gulp.src([
        'static/lib/fastclick.min.js',
        'static/lib/avalon.modern.min.js',
        'static/lib/zepto.min.js',
        'static/js/teacher-detail/Util.js',
        'static/js/teacher-detail/config.js',
        'static/js/teacher-detail/app.js'
    ]).pipe(concat('base.all.js'))
        .pipe(uglify({'preserveComments': 'some'}))
        .pipe(gulp.dest('static/js/teacher-detail'))
});
gulp.task('uglify-js-td-1.4', function () {
    gulp.src('static/js/teacher-detail/teacherDetail-v1.4.js')
        .pipe(uglify({'preserveComments': 'some'}))
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest('static/js/teacher-detail/'))
});

/**我的钱包*/
gulp.task('concat-js-mywallet-1.4', function () {
    del(['static/js/mywallet/mywallet-v1.4.js'], function () {
        gulp.src([
            'static/lib/fastclick.min.js',
            'static/lib/avalon.modern.min.js',
            'static/lib/zepto.min.js',
            'static/lib/scroller-hooks.min.js',
            'static/js/mywallet/mywallet-v1.4.src.js'
        ]).pipe(concat('mywallet-v1.4.js'))
            .pipe(uglify({'preserveComments': 'some'}))
            .pipe(gulp.dest('static/js/mywallet/'))
    })
});
gulp.task('concat-css-mywallet-1.4', function () {
    del(['static/css/mywallet/mywallet-v1.4.css'], function () {
        gulp.src([
            'static/css/common/reset.css',
            'static/css/common/scroller.css',
            'static/css/mywallet/mywallet-v1.4.src.css'
        ]).pipe(concat('mywallet-v1.4.css'))
            .pipe(gulp.dest('static/css/mywallet/'))
    })
});

/**充值返现*/
gulp.task('concat-js-activity-1.4', function () {
    del(['static/js/activity/activity-v1.4.js'], function () {
        gulp.src([
            'static/lib/fastclick.min.js',
            'static/lib/avalon.modern.min.js',
            'static/lib/zepto.min.js',
            'static/lib/scroller-hooks.min.js',
            'static/js/activity/activity-v1.4.src.js'
        ]).pipe(concat('activity-v1.4.js'))
            .pipe(uglify({'preserveComments': 'some'}))
            .pipe(gulp.dest('static/js/activity/'))
    })
});
gulp.task('concat-css-activity-1.4', function () {
    del(['static/css/activity/activity-v1.4.css'], function () {
        gulp.src([
            'static/css/common/reset.css',
            'static/css/common/scroller.css',
            'static/css/activity/activity-v1.4.src.css'
        ]).pipe(concat('activity-v1.4.css'))
            .pipe(gulp.dest('static/css/activity/'))
    })
});
/**我的动态分享*/
gulp.task('concat-js-dynamic-1.4', function () {
    del(['static/js/dynamic/dynamic.js'], function () {
        gulp.src([
            'static/lib/fastclick.min.js',
            'static/lib/avalon.modern.min.js',
            'static/lib/zepto.min.js',
            'static/js/dynamic/dynamic.src.js'
        ]).pipe(concat('dynamic.js'))
            .pipe(uglify({'preserveComments': 'some'}))
            .pipe(gulp.dest('static/js/dynamic/'))
    })
});

gulp.task('watch', function () {
    gulp.watch('static/js/mywallet/mywallet-v1.4.src.js').on('change', function () {
        gulp.start('concat-js-mywallet-1.4');
    });
    gulp.watch('static/css/mywallet/mywallet-v1.4.src.css').on('change', function () {
        gulp.start('concat-css-mywallet-1.4');
    });
    gulp.watch('static/js/activity/activity-v1.4.src.js').on('change', function () {
        gulp.start('concat-js-activity-1.4');
    });
    gulp.watch('static/css/activity/activity-v1.4.src.css').on('change', function () {
        gulp.start('concat-css-activity-1.4');
    });
});

