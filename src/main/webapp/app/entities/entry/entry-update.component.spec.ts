/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EntryUpdate from './entry-update.vue';
import EntryService from './entry.service';
import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';

type EntryUpdateComponentType = InstanceType<typeof EntryUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const entrySample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EntryUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Entry Management Update Component', () => {
    let comp: EntryUpdateComponentType;
    let entryServiceStub: SinonStubbedInstance<EntryService>;

    beforeEach(() => {
      route = {};
      entryServiceStub = sinon.createStubInstance<EntryService>(EntryService);
      entryServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          entryService: () => entryServiceStub,
          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(EntryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.entry = entrySample;
        entryServiceStub.update.resolves(entrySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entryServiceStub.update.calledWith(entrySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        entryServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EntryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.entry = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        entryServiceStub.find.resolves(entrySample);
        entryServiceStub.retrieve.resolves([entrySample]);

        // WHEN
        route = {
          params: {
            entryId: '' + entrySample.id,
          },
        };
        const wrapper = shallowMount(EntryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.entry).toMatchObject(entrySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        entryServiceStub.find.resolves(entrySample);
        const wrapper = shallowMount(EntryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
