/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OperatorRoleDetails from './operator-role-details.vue';
import OperatorRoleService from './operator-role.service';
import AlertService from '@/shared/alert/alert.service';

type OperatorRoleDetailsComponentType = InstanceType<typeof OperatorRoleDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const operatorRoleSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('OperatorRole Management Detail Component', () => {
    let operatorRoleServiceStub: SinonStubbedInstance<OperatorRoleService>;
    let mountOptions: MountingOptions<OperatorRoleDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      operatorRoleServiceStub = sinon.createStubInstance<OperatorRoleService>(OperatorRoleService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          operatorRoleService: () => operatorRoleServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        operatorRoleServiceStub.find.resolves(operatorRoleSample);
        route = {
          params: {
            operatorRoleId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(OperatorRoleDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.operatorRole).toMatchObject(operatorRoleSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        operatorRoleServiceStub.find.resolves(operatorRoleSample);
        const wrapper = shallowMount(OperatorRoleDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
