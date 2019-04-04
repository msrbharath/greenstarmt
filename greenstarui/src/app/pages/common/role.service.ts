import { Injectable } from "@angular/core";

@Injectable()
export class RoleService {

    public isGrantedRole(roles: any): boolean {
        if ((typeof roles !== 'undefined') && null !== roles && '' !== roles && roles.length > 0) {
            if (roles.includes(localStorage.getItem('roleName'))) {
                return true;
            } else {
                return false;
            }
        }
    }

}
