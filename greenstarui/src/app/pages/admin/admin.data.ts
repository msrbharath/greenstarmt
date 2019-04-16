import {CustomRenderComponent} from './admin.customrender.component';
export class AdminData {

    public static PERF_PARAM_DEFAULT: string = 'DEFAULT';
    public static PERF_PARAM_CUSTOM: string = 'CUSTOMIZED';

    public static getUserRoleMappingTableSetting(): any {
        let settings: any = {
            add: {
                addButtonContent: '<i class="nb-plus"></i>',
                createButtonContent: '<i class="nb-checkmark"></i>',
                cancelButtonContent: '<i class="nb-close"></i>',
                confirmCreate: true
            },
            edit: {
                editButtonContent: '<i class="nb-edit"></i>',
                saveButtonContent: '<i class="nb-checkmark"></i>',
                cancelButtonContent: '<i class="nb-close"></i>',
                confirmSave: true
            },
            delete: {
                deleteButtonContent: '<i class="nb-trash"></i>',
                confirmDelete: true
            },
            columns: {
                userId: {
                    title: 'User ID',
                    type: 'string',
                },
                roleName: {
                    title: 'Role',
                    type: 'string',
                    editor: {
                        type: 'list',
                        config: {
                            selectText: 'Select',
                            list: this.getRoles()
                        }
                    }
                },
                schoolNames: {
                    title: 'Allowed Schools',
                    type: 'string',
                    editor: {
                        type: 'custom',
                        component: CustomRenderComponent
                      }
                }
            }
        };

        return settings;
    }

    public static getRoles(): any {

        var roleValues: any[] = [
            { title: 'Admin', value: 'Admin' },
            { title: 'Event POC', value: 'Event POC' },
            { title: 'PMO', value: 'PMO' }
        ];
        return roleValues;
    }

}