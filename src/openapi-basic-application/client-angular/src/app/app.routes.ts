import { Routes } from '@angular/router';

import { ListComponent} from './list/list.component';
import { UserComponent} from './user/user.component';

export const routes: Routes = [
    { pathMatch: 'full', path: '', redirectTo: 'list' },
    { pathMatch: 'full', path: 'list', component: ListComponent },
    { pathMatch: 'full', path: 'user/:id', component: UserComponent },
];
