/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AffiliatedInfoUpdate from './affiliated-info-update.vue';
import AffiliatedInfoService from './affiliated-info.service';
import AlertService from '@/shared/alert/alert.service';

import FileInfoService from '@/entities/file-info/file-info.service';

type AffiliatedInfoUpdateComponentType = InstanceType<typeof AffiliatedInfoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const affiliatedInfoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AffiliatedInfoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('AffiliatedInfo Management Update Component', () => {
    let comp: AffiliatedInfoUpdateComponentType;
    let affiliatedInfoServiceStub: SinonStubbedInstance<AffiliatedInfoService>;

    beforeEach(() => {
      route = {};
      affiliatedInfoServiceStub = sinon.createStubInstance<AffiliatedInfoService>(AffiliatedInfoService);
      affiliatedInfoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          affiliatedInfoService: () => affiliatedInfoServiceStub,
          fileInfoService: () =>
            sinon.createStubInstance<FileInfoService>(FileInfoService, {
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
        const wrapper = shallowMount(AffiliatedInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.affiliatedInfo = affiliatedInfoSample;
        affiliatedInfoServiceStub.update.resolves(affiliatedInfoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(affiliatedInfoServiceStub.update.calledWith(affiliatedInfoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        affiliatedInfoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AffiliatedInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.affiliatedInfo = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(affiliatedInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        affiliatedInfoServiceStub.find.resolves(affiliatedInfoSample);
        affiliatedInfoServiceStub.retrieve.resolves([affiliatedInfoSample]);

        // WHEN
        route = {
          params: {
            affiliatedInfoId: '' + affiliatedInfoSample.id,
          },
        };
        const wrapper = shallowMount(AffiliatedInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.affiliatedInfo).toMatchObject(affiliatedInfoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        affiliatedInfoServiceStub.find.resolves(affiliatedInfoSample);
        const wrapper = shallowMount(AffiliatedInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
