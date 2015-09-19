                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">题目</div>
                    </div>
                    <div class="panel-body">
                        <div class="list-group">
                        <#if questionPojo?exists>
                            <div class="list-group-item">
                                <div class="list-group-item-heading clearfix">
                                    <span class="col-xs-4"><strong>题目id：</strong>${questionPojo.id}</span>
                                    <span class="col-xs-4"><strong>年级:</strong>${questionPojo.learnPhase}</span>
                                    <span class="col-xs-4"><strong>科目：</strong>${questionPojo.realSubject}</span>
                                </div>
                                <div class="list-group-item-text clearfix">
                                    <div class="col-xs-12">
                                    ${questionPojo.content}
                                    </div>
                                </div>
                            </div>
                            <div class="list-group-item">
                                <div class="list-group-item-heading clearfix"><strong class="col-xs-12">答案</strong>
                                </div>
                                <div class="list-group-item-text clearfix">
                                    <div class="col-xs-12">
                                    ${questionPojo.answer}
                                    </div>
                                </div>
                            </div>
                            <div class="list-group-item">
                                <div class="list-group-item-heading clearfix"><strong class="col-xs-12">解题思路</strong>
                                </div>
                                <div class="list-group-item-text clearfix">
                                    <div class="col-xs-12">
                                    ${questionPojo.solution}
                                    </div>
                                </div>
                            </div>
                            <div class="list-group-item">
                                <div class="list-group-item-heading clearfix"><strong class="col-xs-12">知识点</strong>
                                </div>
                                <div class="list-group-item-text clearfix">
                                    <div class="col-xs-12">
                                        [${questionPojo.realSubject}] ${questionPojo.knowledge}
                                    </div>
                                </div>
                            </div>
                        <#elseif audioDetailVO.questionId?exists>
                            <span class="text-danger">题目id：${audioDetailVO.questionId}</span>
                            <span class="text-danger">题目信息不存在 </span>
                        <#else>
                            <span class="text-danger">题目信息不存在 </span>
                        </#if>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">音频信息</div>
                    </div>
                <#if audioDetailVO?exists>
                    <div class="panel-body">
                        <div class="clearfix">
                            <span class="col-xs-2"><strong>音频ID：</strong>${audioDetailVO.audioId}</span>
                            <span class="col-xs-2"><strong>音频来源：</strong>${audioDetailVO.source}</span>
                            <span class="col-xs-2"><strong>录音老师：</strong>${audioDetailVO.teacherName}</span>
                            <span class="col-xs-3"><strong>手机号：</strong>${audioDetailVO.phoneNumber}</span>
                            <span class="col-xs-3"><strong>录制时间：</strong>${audioDetailVO.createTime?string("yyyy-MM-dd HH:mm:ss")}</span>
                        </div>
                        <div class="audio-player">
                            <audio src="${audioDetailVO.url}"
                                   class="audio-widget" controls="controls"></audio>
                        </div>
                    </div>
                <#else >
                    <span class="text-danger">音频id：${audioId}</span>
                    <span class="text-danger">音频信息不存在 </span>
                </#if>
                </div>