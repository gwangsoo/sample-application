/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RewardItemUpdate from './reward-item-update.vue';
import RewardItemService from './reward-item.service';
import AlertService from '@/shared/alert/alert.service';

import FileInfoService from '@/entities/file-info/file-info.service';
import RewardDetailService from '@/entities/reward-detail/reward-detail.service';

type RewardItemUpdateComponentType = InstanceType<typeof RewardItemUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rewardItemSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RewardItemUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RewardItem Management Update Component', () => {
    let comp: RewardItemUpdateComponentType;
    let rewardItemServiceStub: SinonStubbedInstance<RewardItemService>;

    beforeEach(() => {
      route = {};
      rewardItemServiceStub = sinon.createStubInstance<RewardItemService>(RewardItemService);
      rewardItemServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rewardItemService: () => rewardItemServiceStub,
          fileInfoService: () =>
            sinon.createStubInstance<FileInfoService>(FileInfoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          rewardDetailService: () =>
            sinon.createStubInstance<RewardDetailService>(RewardDetailService, {
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
        const wrapper = shallowMount(RewardItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rewardItem = rewardItemSample;
        rewardItemServiceStub.update.resolves(rewardItemSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rewardItemServiceStub.update.calledWith(rewardItemSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rewardItemServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RewardItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rewardItem = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rewardItemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rewardItemServiceStub.find.resolves(rewardItemSample);
        rewardItemServiceStub.retrieve.resolves([rewardItemSample]);

        // WHEN
        route = {
          params: {
            rewardItemId: '' + rewardItemSample.id,
          },
        };
        const wrapper = shallowMount(RewardItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rewardItem).toMatchObject(rewardItemSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rewardItemServiceStub.find.resolves(rewardItemSample);
        const wrapper = shallowMount(RewardItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
