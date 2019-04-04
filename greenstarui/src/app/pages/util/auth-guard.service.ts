
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class AuthGuardService implements CanActivate {

    private returnVal: boolean = true;

    constructor(private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

        let currentUser = localStorage.getItem('userId');

        // if block to check the current user is still available.
        if ((typeof currentUser === 'undefined') || (null === currentUser) || (currentUser.trim().length === 0)) {
            // not logged in so return false
            localStorage.clear();
            location.href = '/greenstarui/login';
            this.returnVal = false;
        } else {
            this.returnVal = true;
        }
        return this.returnVal;
    }

}
