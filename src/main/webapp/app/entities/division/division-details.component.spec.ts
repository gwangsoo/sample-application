/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DivisionDetails from './division-details.vue';
import DivisionService from './division.service';
import AlertService from '@/shared/alert/alert.service';

type DivisionDetailsComponentType = InstanceType<typeof DivisionDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const divisionSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Division Management Detail Component', () => {
    let divisionServiceStub: SinonStubbedInstance<DivisionService>;
    let mountOptions: MountingOptions<DivisionDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      divisionServiceStub = sinon.createStubInstance<DivisionService>(DivisionService);

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
          divisionService: () => divisionServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        divisionServiceStub.find.resolves(divisionSample);
        route = {
          params: {
            divisionId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(DivisionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.division).toMatchObject(divisionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        divisionServiceStub.find.resolves(divisionSample);
        const wrapper = shallowMount(DivisionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
