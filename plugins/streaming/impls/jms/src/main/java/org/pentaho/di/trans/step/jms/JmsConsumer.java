/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2018 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.step.jms;

import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.value.ValueMetaString;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.streaming.common.BaseStreamStep;
import org.pentaho.di.trans.streaming.common.FixedTimeStreamWindow;

import static java.util.Objects.requireNonNull;

public class JmsConsumer extends BaseStreamStep {


  public JmsConsumer( StepMeta stepMeta,
                      StepDataInterface stepDataInterface, int copyNr,
                      TransMeta transMeta, Trans trans ) {
    super( stepMeta, stepDataInterface, copyNr, transMeta, trans );
  }

  public boolean init( StepMetaInterface stepMetaInterface, StepDataInterface stepDataInterface ) {
    boolean superStatus = super.init( stepMetaInterface, stepDataInterface );

    JmsConsumerMeta meta = (JmsConsumerMeta) stepMetaInterface;

    RowMeta rowMeta = new RowMeta();
    rowMeta.addValueMeta( new ValueMetaString( "message" ) );

    window = new FixedTimeStreamWindow<>( subtransExecutor, rowMeta, getDuration(), getBatchSize() );
    source = new JmsStreamSource( this, requireNonNull( meta.jmsDelegate ) );
    return superStatus;
  }

}
