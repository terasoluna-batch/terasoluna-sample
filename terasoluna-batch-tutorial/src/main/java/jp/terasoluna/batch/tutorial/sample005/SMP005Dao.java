/*
 * Copyright (c) 2015 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.terasoluna.batch.tutorial.sample005;

import org.apache.ibatis.session.ResultHandler;

public interface SMP005Dao {

    /**
     * 入出金情報を取得する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    public void collectNyusyukkinData(Object object, ResultHandler handler);
}
