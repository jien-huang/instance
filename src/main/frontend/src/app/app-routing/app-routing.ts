import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ScriptComponent } from '../script/script.component';
import { ResultComponent } from '../result/result.component';
import { ConfigurationComponent} from '../configuration/configuration.component';

const routes: Routes = [
  {
        path: 'script',
        component: ScriptComponent
    },
    {
            path: 'result',
            component: ResultComponent
        },
        {
                path: 'configuration',
                component: ConfigurationComponent
            }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule, RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
