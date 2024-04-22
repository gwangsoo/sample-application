/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EntryDetails from './entry-details.vue';
import EntryService from './entry.service';
import AlertService from '@/shared/alert/alert.service';

type EntryDetailsComponentType = InstanceType<typeof EntryDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const entrySample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Entry Management Detail Component', () => {
    let entryServiceStub: SinonStubbedInstance<EntryService>;
    let mountOptions: MountingOptions<EntryDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      entryServiceStub = sinon.createStubInstance<EntryService>(EntryService);

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
          entryService: () => entryServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        entryServiceStub.find.resolves(entrySample);
        route = {
          params: {
            entryId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(EntryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.entry).toMatchObject(entrySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        entryServiceStub.find.resolves(entrySample);
        const wrapper = shallowMount(EntryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
